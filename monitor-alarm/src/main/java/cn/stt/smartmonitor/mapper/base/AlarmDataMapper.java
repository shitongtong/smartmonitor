package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.AlarmData;

public interface AlarmDataMapper {
    int deleteByPrimaryKey(String alarmId);

    int insert(AlarmData record);

    int insertSelective(AlarmData record);

    AlarmData selectByPrimaryKey(String alarmId);

    int updateByPrimaryKeySelective(AlarmData record);

    int updateByPrimaryKey(AlarmData record);
}