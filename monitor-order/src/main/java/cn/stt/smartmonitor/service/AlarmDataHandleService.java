package cn.stt.smartmonitor.service;


import cn.stt.smartmonitor.dto.AlarmDataDto;

/**
 * @ClassName AlarmDataService
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/18 18:19
 * @Version 1.0
 */
public interface AlarmDataHandleService {

    /**
     * 处理告警数据
     *
     * @param alarmDataDto
     */
    void handAlarmData(AlarmDataDto alarmDataDto);
}
