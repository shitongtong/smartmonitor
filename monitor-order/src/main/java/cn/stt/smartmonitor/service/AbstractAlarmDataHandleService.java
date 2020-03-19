package cn.stt.smartmonitor.service;

import cn.stt.smartmonitor.dto.AlarmDataDto;
import cn.stt.smartmonitor.dto.AlarmDataFullDto;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName AbstractAlarmDataHandleService
 * @Description TODO
 * @Author shitt7
 * @Date 2019/5/7 13:35
 * @Version 1.0
 */
@Component
public abstract class AbstractAlarmDataHandleService implements AlarmDataHandleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAlarmDataHandleService.class);

    @Autowired
    private AlarmDataService alarmDataService;
    @Autowired
    private AlarmOrderService alarmOrderService;

    /**
     * 告警设备类型
     *
     * @return
     */
    protected abstract AlarmDataEnum.AlarmType getAlarmType();

    @Override
    public void handAlarmData(AlarmDataDto alarmDataDto) {
        //告警数据补全
        AlarmDataFullDto alarmDataFullDto = completeData(alarmDataDto);
        if (alarmDataFullDto != null) {
            //告警入库
            boolean flag = alarmDataService.saveAlarmData(alarmDataFullDto);
            if (flag) {
                Integer alarmStatus = alarmDataFullDto.getAlarmStatus();
                if (AlarmDataEnum.AlarmStatus.ACTIVITY.key == alarmStatus) {
                    alarmOrderService.sendAlarmData(alarmDataFullDto);
                } else if (AlarmDataEnum.AlarmStatus.CLEAR.key == alarmStatus) {
                    alarmOrderService.sendClearAlarmData(alarmDataFullDto);
                } else {
                    LOGGER.info("未知告警{}状态:{}", alarmDataFullDto.getAlarmId(), alarmStatus);
                }
            }
        }
    }

    /**
     * 告警数据补全
     *
     * @param alarmDataDto
     * @return
     */
    protected abstract AlarmDataFullDto completeData(AlarmDataDto alarmDataDto) ;

}
