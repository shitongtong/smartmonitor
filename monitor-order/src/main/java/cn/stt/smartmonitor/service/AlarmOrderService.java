package cn.stt.smartmonitor.service;


import cn.stt.smartmonitor.dto.AlarmDataFullDto;

/**
 * @ClassName AlarmOrderService
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/23 9:11
 * @Version 1.0
 */
public interface AlarmOrderService {

    /**
     * 派发活跃告警到工单
     *
     * @param alarmDataFullDto
     */
    void sendAlarmData(AlarmDataFullDto alarmDataFullDto);

    /**
     * 派发清除告警到工单
     *
     * @param alarmDataFullDto
     */
    void sendClearAlarmData(AlarmDataFullDto alarmDataFullDto);
}
