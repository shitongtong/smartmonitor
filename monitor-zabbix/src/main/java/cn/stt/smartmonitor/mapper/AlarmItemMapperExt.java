package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmItem;
import cn.stt.smartmonitor.mapper.base.AlarmItemMapper;

import java.util.List;

public interface AlarmItemMapperExt extends AlarmItemMapper {

    List<AlarmItem> findByApplicationName(String applicationName);
}