package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.mapper.base.AlarmApplicationMapper;

import java.util.List;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/11 15:57
 */
public interface AlarmApplicationMapperExt extends AlarmApplicationMapper {
    List<String> findApplicationNameByAlarmGroupId(int alarmGroupId);
}
