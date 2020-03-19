package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmRules;
import cn.stt.smartmonitor.mapper.base.AlarmRulesMapper;

public interface AlarmRulesMapperExt extends AlarmRulesMapper {

    AlarmRules findByItemName(String itemName);
}