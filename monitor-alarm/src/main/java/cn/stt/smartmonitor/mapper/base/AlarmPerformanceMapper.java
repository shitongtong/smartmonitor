package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.AlarmPerformance;

public interface AlarmPerformanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmPerformance record);

    int insertSelective(AlarmPerformance record);

    AlarmPerformance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmPerformance record);

    int updateByPrimaryKey(AlarmPerformance record);
}