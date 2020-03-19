package cn.stt.smartmonitor.service.impl;

import cn.stt.common.util.DateUtil;
import cn.stt.common.util.HttpUtil;
import cn.stt.common.util.TransferUtil;
import cn.stt.smartmonitor.config.YmlMyConfig;
import cn.stt.smartmonitor.constant.RedisConstant;
import cn.stt.smartmonitor.dto.AlarmDataDto;
import cn.stt.smartmonitor.entity.AlarmData;
import cn.stt.smartmonitor.entity.AlarmRules;
import cn.stt.smartmonitor.entity.ItemData;
import cn.stt.smartmonitor.entity.ItemDataHistory;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import cn.stt.smartmonitor.mapper.AlarmDataMapperExt;
import cn.stt.smartmonitor.mapper.AlarmPerformanceMapperExt;
import cn.stt.smartmonitor.mapper.AlarmRulesMapperExt;
import cn.stt.smartmonitor.mapper.ItemDataHistoryMapperExt;
import cn.stt.smartmonitor.mapper.ItemDataMapperExt;
import cn.stt.smartmonitor.service.MonitorAlarmService;
import cn.stt.smartmonitor.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/13 11:23
 */
@Service
@Slf4j
public class MonitorAlarmServiceImpl implements MonitorAlarmService {
    @Autowired
    private ItemDataMapperExt itemDataMapperExt;
    @Autowired
    private ItemDataHistoryMapperExt itemDataHistoryMapperExt;
    @Autowired
    private AlarmPerformanceMapperExt alarmPerformanceMapperExt;
    @Autowired
    private AlarmRulesMapperExt alarmRulesMapperExt;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AlarmDataMapperExt alarmDataMapperExt;
    @Autowired
    private YmlMyConfig ymlMyConfig;

    @Override
    public void handleZabbixData(String zabbixData) {
        ItemData itemData = JSON.parseObject(zabbixData, ItemData.class);
        saveItemData2db(itemData);
        handleAlarmData(itemData);
    }

    private void handleAlarmData(ItemData itemData) {
        String name = itemData.getName();
        //判断是否发告警
        AlarmRules alarmRules = alarmRulesMapperExt.findByItemName(name);
        if (alarmRules == null) {
            log.info("[{}]告警规则不存在!", name);
            return;
        }
        Integer useStatus = alarmRules.getUseStatus();
        if (useStatus == 0) {
            log.info("[{}]告警规则未启用!", name);
            return;
        }
        Date startTime = alarmRules.getStartTime();
        Date endTime = alarmRules.getEndTime();
        if (startTime == null || endTime == null) {
            log.info("[{}]告警规则时间未设置!", name);
            return;
        }
        Date nowDate = new Date();
        if (DateUtil.compareDate(startTime, nowDate) > 0 || DateUtil.compareDate(endTime, nowDate) < 0) {
            log.info("[{}]告警规则已过期!", name);
            return;
        }
        String ip = itemData.getIp();
        String value = itemData.getValue();
        String alarmCountRedisKey = RedisConstant.ALARM_COUNT_KEY + name + ":" + ip;
        String clearCountRedisKey = RedisConstant.CLEAR_COUNT_KEY + name + ":" + ip;
        Integer thresholdStatus = alarmRules.getThresholdStatus();
        int alarmCount = alarmRules.getAlarmCount();
        int clearCount = alarmRules.getClearCount();
        if (thresholdStatus == 0) {
            //不需要阈值,则value的值应为0或1，否则非法
            if (Integer.parseInt(value) == 0) {
                //是活跃告警
                //清除次数置0
                redisUtil.set(clearCountRedisKey, 0);
                //告警次数+1
                long incr = redisUtil.incr(alarmCountRedisKey);
                if (incr >= alarmCount) {
                    //达到次数，发送告警
                    sendAlarmData(itemData, alarmRules);
                }
            } else {
                //正常，清除告警
                //告警次数置0
                redisUtil.set(alarmCountRedisKey, 0);
                //清除次数+1
                long incr = redisUtil.incr(clearCountRedisKey);
                if (incr >= clearCount) {
                    //达到次数，发送清除告警
                    sendClearAlarmData(itemData);
                }
            }
        } else {
            //需要阈值
            int lowerThreshold = alarmRules.getLowerThreshold();
            int threshold = alarmRules.getThreshold();
            double v = Double.parseDouble(value);
            if (v <= lowerThreshold || v >= threshold) {
                //是活跃告警
                //清除次数置0
                redisUtil.set(clearCountRedisKey, 0);
                //告警次数+1
                long incr = redisUtil.incr(alarmCountRedisKey);
                if (incr >= alarmCount) {
                    //达到次数，发送告警
                    sendAlarmData(itemData, alarmRules);
                }
            }else {
                //正常，清除告警
                //告警次数置0
                redisUtil.set(alarmCountRedisKey, 0);
                //清除次数+1
                long incr = redisUtil.incr(clearCountRedisKey);
                if (incr >= clearCount) {
                    //达到次数，发送清除告警
                    sendClearAlarmData(itemData);
                }
            }
        }
    }

    private void sendClearAlarmData(ItemData itemData) {
        //判断告警是否已存在,不存在则不发清除告警
        String ip = itemData.getIp();
        String name = itemData.getName();
        String port = itemData.getPort();
        String value = itemData.getValue();
        AlarmData alarmData = alarmDataMapperExt.findActivityUnique(ip, name, port);
        if (alarmData == null) {
            log.info("{}-{}-{}告警不存在,不清除!", ip, itemData, port);
            return;
        }
        //封装清除告警
        AlarmDataDto alarmDataDto = TransferUtil.transfer(alarmData, AlarmDataDto.class);
        String alarmId = getAlarmId(ip + "clear");
        alarmDataDto.setAlarmId(alarmId);
        //clearId为原告警id
        alarmDataDto.setClearId(alarmData.getAlarmId());
        alarmDataDto.setClearTime(DateFormatUtils.format(new Date(), DateUtil.YYYYMMDDHHMMSS));
        alarmDataDto.setAlarmTitle("清除告警: " + alarmData.getAlarmTitle());
        alarmDataDto.setAlarmText("清除告警: " + alarmData.getAlarmText());
        alarmDataDto.setAlarmStatus(AlarmDataEnum.AlarmStatus.CLEAR.key);
        String alarmDataJson = JSON.toJSONString(alarmDataDto);
        try {
            log.info("发送清除告警参数:{}", alarmDataJson);
            String result = HttpUtil.sendPost(ymlMyConfig.getAlarmHandUrl(), alarmDataJson);
            log.info("发送清除告警结果:{}", result);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void sendAlarmData(ItemData itemData, AlarmRules alarmRules) {
        //判断告警是否已存在,存在则不发告警
        String ip = itemData.getIp();
        String name = itemData.getName();
        String port = itemData.getPort();
        String value = itemData.getValue();
        AlarmData alarmData = alarmDataMapperExt.findActivityUnique(ip, name, port);
        if (alarmData != null) {
            log.info("{}-{}-{}告警已存在,不发告警!", ip, itemData, port);
            return;
        }
        String eqpTypeName = alarmRules.getEqpTypeName();
        String performanceName = alarmRules.getPerformanceName();
        int lowerThreshold = alarmRules.getLowerThreshold();
        int threshold = alarmRules.getThreshold();
        String alarmTitle = eqpTypeName + performanceName + "告警";
        int thresholdStatus = alarmRules.getThresholdStatus();
        String alarmText = "";
        if (thresholdStatus == 0) {
            //无阈值
            alarmText = ip + performanceName + "告警";
            if (StringUtils.isNotBlank(port)) {
                alarmText = ip + port + performanceName + "告警";
            }
        } else {
            alarmText = ip + performanceName + "," + value + "不在阈值范围" + lowerThreshold + "-" + threshold + "内";
        }

        //封装告警数据
        AlarmDataDto alarmDataDto = new AlarmDataDto();
        String alarmId = getAlarmId(ip + alarmText);
        alarmDataDto.setAlarmId(alarmId);
        alarmDataDto.setClearId("0");
        alarmDataDto.setEventTime(DateFormatUtils.format(new Date(), DateUtil.YYYYMMDDHHMMSS));
        alarmDataDto.setAlarmTitle(alarmTitle);
        alarmDataDto.setAlarmText(alarmText);
        alarmDataDto.setAlarmStatus(AlarmDataEnum.AlarmStatus.ACTIVITY.key);
        alarmDataDto.setAlarmType(AlarmDataEnum.AlarmType.SERVER_OS.key);
        alarmDataDto.setIp(ip);
        Integer alarmLevel = alarmRules.getAlarmLevel();
        Integer sendStatus = alarmRules.getSendStatus();
        alarmDataDto.setAlarmLevel(alarmLevel);
        alarmDataDto.setSendStatus(sendStatus);
        alarmDataDto.setFaultType(performanceName);
        String alarmDataJson = JSON.toJSONString(alarmDataDto);
        try {
            log.info("发送告警参数:{}", alarmDataJson);
            String result = HttpUtil.sendPost(ymlMyConfig.getAlarmHandUrl(), alarmDataJson);
            log.info("发送告警结果:{}", result);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 获取告警Id
     *
     * @param content
     * @return
     */
    private String getAlarmId(String content) {
        return Math.abs(content.hashCode()) + "_" + System.currentTimeMillis();
    }

    @Transactional(rollbackFor = Exception.class)
    private void saveItemData2db(ItemData itemData) {
        ItemData unique = itemDataMapperExt.findUnique(itemData.getIp(), itemData.getName(), itemData.getPort());
        if (unique == null) {
            itemDataMapperExt.insertSelective(itemData);
        } else {
            itemData.setId(unique.getId());
            itemDataMapperExt.updateByPrimaryKeySelective(itemData);
        }
        ItemDataHistory itemDataHistory = TransferUtil.transfer(itemData, ItemDataHistory.class);
        itemDataHistoryMapperExt.insertSelective(itemDataHistory);
    }
}
