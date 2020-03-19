package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.AlarmOrder;

public interface AlarmOrderMapper {
    int deleteByPrimaryKey(String alarmId);

    int insert(AlarmOrder record);

    int insertSelective(AlarmOrder record);

    AlarmOrder selectByPrimaryKey(String alarmId);

    int updateByPrimaryKeySelective(AlarmOrder record);

    int updateByPrimaryKey(AlarmOrder record);
}