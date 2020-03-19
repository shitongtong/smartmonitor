package cn.stt.smartmonitor.service.impl;


import cn.stt.common.util.HttpUtil;
import cn.stt.smartmonitor.config.YmlMyConfig;
import cn.stt.smartmonitor.dto.AlarmDataFullDto;
import cn.stt.smartmonitor.dto.OrderDto;
import cn.stt.smartmonitor.entity.AlarmOrder;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import cn.stt.smartmonitor.enums.AlarmOrderEnum;
import cn.stt.smartmonitor.mapper.base.AlarmDataMapper;
import cn.stt.smartmonitor.mapper.base.AlarmOrderMapper;
import cn.stt.smartmonitor.response.OrderResponse;
import cn.stt.smartmonitor.service.AlarmOrderService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AlarmOrderServiceImpl
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/23 9:12
 * @Version 1.0
 */
@Service
public class AlarmOrderServiceImpl implements AlarmOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDataServiceImpl.class);
    @Autowired
    private AlarmOrderMapper alarmOrderMapper;
    @Autowired
    private AlarmDataMapper alarmDataMapper;
    @Autowired
    private YmlMyConfig ymlMyConfig;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendAlarmData(AlarmDataFullDto alarmDataFullDto) {
        Integer alarmStatus = alarmDataFullDto.getAlarmStatus();
        Integer sendStatus = alarmDataFullDto.getSendStatus();
        if (sendStatus == null) {
            sendStatus = 1;
        }
        if (AlarmDataEnum.AlarmStatus.ACTIVITY.key == alarmStatus && 1 == sendStatus) {
            //是活跃告警并且告警设置为发送告警
            AlarmOrder alarmOrder = sendOrder(alarmDataFullDto);
            //保存派发结果
            alarmOrderMapper.insertSelective(alarmOrder);
            //更新诊断结果
            /*AlarmData alarmData = new AlarmData();
            alarmData.setAlarmId(alarmDataFullDto.getAlarmId());
            alarmData.setDiagnoseResult(alarmDataFullDto.getDiagnoseResult());
            alarmDataMapper.updateByPrimaryKeySelective(alarmData);*/
        } else {
            LOGGER.info("不派发告警: alarmStatus={},sendStatus={}", alarmStatus, sendStatus);
        }

    }

    @Override
    public void sendClearAlarmData(AlarmDataFullDto alarmDataFullDto) {
        Integer alarmStatus = alarmDataFullDto.getAlarmStatus();
        Integer sendStatus = alarmDataFullDto.getSendStatus();
        if (sendStatus == null) {
            sendStatus = 1;
        }
        //告警清除通知
        if (AlarmDataEnum.AlarmStatus.CLEAR.key == alarmStatus && 1 == sendStatus) {
            //是清除告警并且告警设置为发送告警
            sendClearOrder(alarmDataFullDto);
        }
    }

    /**
     * 清除告警通知工单
     *
     * @param alarmDataFullDto
     */
    private void sendClearOrder(AlarmDataFullDto alarmDataFullDto) {
        try {
            String clearId = alarmDataFullDto.getClearId();
            // 活动告警调用智能诊断5分钟内，存在清除告警告警发送过来
            AlarmOrder alarmOrder = alarmOrderMapper.selectByPrimaryKey(clearId);
            if (alarmOrder == null) {
                //总共等待6分钟
                for (int i = 0; i < 6; i++) {
                    //等待1分钟
                    Thread.sleep(60000);
                    alarmOrder = alarmOrderMapper.selectByPrimaryKey(clearId);
                    if (alarmOrder != null) {
                        break;
                    }
                }
            }
            if (alarmOrder == null) {
                LOGGER.info("关联告警{}清除，无需通知工单", clearId);
                return;
            }
            String orderNo = alarmOrder.getOrderNo();
            String clearTime = alarmDataFullDto.getClearTime();
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("orderNumber", orderNo);
            paramMap.put("alarmClearTime", clearTime);
            LOGGER.info("派发清除工单请求参数={}",  paramMap);
            String paramJson = JSON.toJSONString(paramMap);
            String result = HttpUtil.sendPost(ymlMyConfig.getSendClearOrderUrl(),paramJson);
            LOGGER.info("派发清除工单返回结果:{}", result);
            JSONObject jsonObject = JSON.parseObject(result);
            String code = jsonObject.getString("code");
            if ("0".equals(code)) {
                LOGGER.info("派发清除工单{}成功", orderNo);
            } else {
                LOGGER.error("派发清除工单{}失败:{}", orderNo, jsonObject.getString("errorMsg"));
            }
        } catch (Exception e) {
            LOGGER.error("派发清除工单异常", e);
        }
    }

    /**
     * 派发告警工单
     *
     * @param alarmDataFullDto
     */
    private AlarmOrder sendOrder(AlarmDataFullDto alarmDataFullDto) {
        AlarmOrder alarmOrder = new AlarmOrder();
        alarmOrder.setAlarmId(alarmDataFullDto.getAlarmId());
        try {
            OrderDto orderDto = getOrderInfo(alarmDataFullDto);
            String orderParam = JSON.toJSONString(orderDto);
            LOGGER.info("派单请求参数:{}", orderParam);
            String response = HttpUtil.sendPost(ymlMyConfig.getSendOrderUrl(), orderParam);
            LOGGER.info("派单返回结果:{}", response);
            OrderResponse orderResponse = JSON.parseObject(response, OrderResponse.class);
            if ("0".equals(orderResponse.getCode())) {
                alarmOrder.setOrderNo(orderResponse.getData().getOrderNumber());
                alarmOrder.setSendStatus(AlarmOrderEnum.SendStatus.SUCCESS.key);
                alarmOrder.setOrderStatus(AlarmOrderEnum.OrderStatus.REPAIR.key);
                alarmOrder.setSendResult("派发成功");
            } else {
                LOGGER.error("派发工单失败:{}", response);
                alarmOrder.setSendStatus(AlarmOrderEnum.SendStatus.FAIL.key);
                alarmOrder.setSendResult(response);
            }
        } catch (Exception e) {
            LOGGER.error("派发工单异常", e);
            alarmOrder.setSendStatus(AlarmOrderEnum.SendStatus.FAIL.key);
            alarmOrder.setSendResult(e.getMessage());
        }
        return alarmOrder;
    }

    /**
     * 组装工单流程系统所需数据
     *
     * @param alarmDataFullDto
     * @return
     */
    private OrderDto getOrderInfo(AlarmDataFullDto alarmDataFullDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setAlarmId(alarmDataFullDto.getAlarmId());
        orderDto.setEqpType(alarmDataFullDto.getEqpType());
        orderDto.setAlarmType(alarmDataFullDto.getAlarmType());
        orderDto.setAlarmTime(alarmDataFullDto.getEventTime());
        orderDto.setAssetNumber(alarmDataFullDto.getAssetNumber());
        orderDto.setAlarmContent(alarmDataFullDto.getAlarmText());
        orderDto.setFaultType(alarmDataFullDto.getFaultType());
        orderDto.setCrackingType(alarmDataFullDto.getCrackingType());
        orderDto.setPortCode(alarmDataFullDto.getPortCode());
        return orderDto;
    }
}
