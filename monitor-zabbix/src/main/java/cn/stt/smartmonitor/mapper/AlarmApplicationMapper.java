package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmApplication;

public interface AlarmApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmApplication record);

    int insertSelective(AlarmApplication record);

    AlarmApplication selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmApplication record);

    int updateByPrimaryKey(AlarmApplication record);
}