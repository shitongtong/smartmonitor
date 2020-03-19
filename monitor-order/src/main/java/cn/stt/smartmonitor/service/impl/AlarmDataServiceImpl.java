package cn.stt.smartmonitor.service.impl;


import cn.stt.common.util.DateUtil;
import cn.stt.common.util.TransferUtil;
import cn.stt.smartmonitor.dto.AlarmDataFullDto;
import cn.stt.smartmonitor.entity.AlarmData;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import cn.stt.smartmonitor.mapper.base.AlarmDataMapper;
import cn.stt.smartmonitor.service.AlarmDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AlarmDataServiceImpl
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/18 18:19
 * @Version 1.0
 */
@Service
public class AlarmDataServiceImpl implements AlarmDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmDataServiceImpl.class);

    @Autowired
    private AlarmDataMapper alarmDataMapper;

    @Override
    public boolean saveAlarmData(AlarmDataFullDto alarmDataFullDto) {
        try {
            AlarmData alarmData = TransferUtil.transfer(alarmDataFullDto, AlarmData.class);
            String eventTime = alarmDataFullDto.getEventTime();
            String clearTime = alarmDataFullDto.getClearTime();
            if (StringUtils.isNotBlank(eventTime)) {
                alarmData.setEventTime(DateUtils.parseDate(eventTime, DateUtil.YYYYMMDDHHMMSS));
            }
            if (StringUtils.isNotBlank(clearTime)) {
                alarmData.setClearTime(DateUtils.parseDate(clearTime, DateUtil.YYYYMMDDHHMMSS));
            }
//            alarmData.setDiagnoseResult("");
            Integer alarmStatus = alarmData.getAlarmStatus();
            String clearId = alarmData.getClearId();
            if (AlarmDataEnum.AlarmStatus.ACTIVITY.key == alarmStatus) {
                alarmDataMapper.insertSelective(alarmData);
            } else if (AlarmDataEnum.AlarmStatus.CLEAR.key == alarmStatus) {
                //清除告警
                //1、查看是否存在活跃告警 clearId等于原活跃告警的alarmId
                AlarmData data = alarmDataMapper.selectByPrimaryKey(clearId);
                if (data == null) {
                    //2、不存在
                    //就丢了
                    return false;
                } else {
                    //3、存在
                    AlarmData aData = new AlarmData();
                    aData.setAlarmId(clearId);
                    aData.setClearId(clearId);
                    aData.setAlarmStatus(alarmStatus);
                    aData.setClearTime(alarmData.getClearTime());
                    alarmDataMapper.updateByPrimaryKeySelective(aData);
                }
            } else {
                LOGGER.error("无效的告警状态:{}", alarmStatus);
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

}
