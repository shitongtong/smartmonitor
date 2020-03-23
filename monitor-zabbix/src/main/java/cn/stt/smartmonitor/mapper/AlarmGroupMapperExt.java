package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.mapper.base.AlarmGroupMapper;

import java.util.List;

public interface AlarmGroupMapperExt extends AlarmGroupMapper {

    List<String> findGroupList();
}