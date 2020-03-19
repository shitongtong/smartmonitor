package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmItem;

public interface AlarmItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmItem record);

    int insertSelective(AlarmItem record);

    AlarmItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmItem record);

    int updateByPrimaryKey(AlarmItem record);
}