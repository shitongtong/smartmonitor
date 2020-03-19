package cn.stt.smartmonitor.service.impl;

import cn.stt.common.util.TransferUtil;
import cn.stt.smartmonitor.constant.RedisConstant;
import cn.stt.smartmonitor.dto.AlarmDataDto;
import cn.stt.smartmonitor.dto.AlarmDataFullDto;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import cn.stt.smartmonitor.service.AbstractAlarmDataHandleService;
import cn.stt.smartmonitor.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServerAlarmDataHandleServiceImpl extends AbstractAlarmDataHandleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAlarmDataHandleServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public AlarmDataEnum.AlarmType getAlarmType() {
        return AlarmDataEnum.AlarmType.SERVER_OS;
    }

    @Override
    public AlarmDataFullDto completeData(AlarmDataDto alarmDataDto) {
        AlarmDataFullDto alarmDataFullDto = TransferUtil.transfer(alarmDataDto, AlarmDataFullDto.class);
        if (AlarmDataEnum.AlarmStatus.CLEAR.key == alarmDataFullDto.getAlarmStatus()) {
            //清除告警不需要补全数据
            return alarmDataFullDto;
        }
        String ip = alarmDataFullDto.getIp();
        alarmDataFullDto.setAssetNumber(ip);
        alarmDataFullDto.setAlarmType(getAlarmType().key);
        String faultType = alarmDataDto.getFaultType();
        alarmDataFullDto.setFaultType(faultType);
        String crackingType = alarmDataDto.getCrackingType();
        if (StringUtils.isBlank(crackingType)) {
            crackingType = faultType;
        }
        alarmDataFullDto.setCrackingType(crackingType);
        alarmDataFullDto.setDiagnoseResult("智能诊断结果：服务器故障");

        String eqpMsg = redisUtil.hmGet(RedisConstant.FORTSERVERPO_REDISKEY, ip);
        return completeEqpMsg(eqpMsg, alarmDataFullDto);

    }

    private AlarmDataFullDto completeEqpMsg(String eqpMsg, AlarmDataFullDto alarmDataFullDto) {
        JSONObject eqpJson;
        try {
            eqpJson = JSON.parseObject(eqpMsg);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return alarmDataFullDto;
        }
        alarmDataFullDto.setFacilityName(eqpJson.getString("deviceName"));
        alarmDataFullDto.setLocation(eqpJson.getString("installAddr"));
        alarmDataFullDto.setResType(eqpJson.getInteger("resourceCategoryId"));
        alarmDataFullDto.setResTypeName(eqpJson.getString("resourceCategoryName"));
        alarmDataFullDto.setEqpType(eqpJson.getInteger("deviceCategoryId"));
        alarmDataFullDto.setEqpTypeName(eqpJson.getString("deviceCategoryName"));

        return alarmDataFullDto;
    }
}
