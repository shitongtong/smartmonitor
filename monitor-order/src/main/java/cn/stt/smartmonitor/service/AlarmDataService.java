package cn.stt.smartmonitor.service;


import cn.stt.smartmonitor.dto.AlarmDataFullDto;

/**
 * @ClassName AlarmDataService
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/18 18:19
 * @Version 1.0
 */
public interface AlarmDataService {

    /**
     * 保存告警数据
     * @param alarmDataFullDto
     * @return true保存成功 false失败
     */
    boolean saveAlarmData(AlarmDataFullDto alarmDataFullDto);
}
