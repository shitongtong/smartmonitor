package cn.stt.smartmonitor.mapper;

import java.util.List;

public interface AlarmGroupMapperExt extends AlarmGroupMapper {

    List<String> findGroupList();
}