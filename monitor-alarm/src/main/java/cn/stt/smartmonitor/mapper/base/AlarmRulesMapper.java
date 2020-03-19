package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.AlarmRules;

public interface AlarmRulesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmRules record);

    int insertSelective(AlarmRules record);

    AlarmRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmRules record);

    int updateByPrimaryKey(AlarmRules record);
}