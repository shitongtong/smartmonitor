package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmGroup;

public interface AlarmGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmGroup record);

    int insertSelective(AlarmGroup record);

    AlarmGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmGroup record);

    int updateByPrimaryKey(AlarmGroup record);
}