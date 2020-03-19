package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.AlarmData;
import cn.stt.smartmonitor.mapper.base.AlarmDataMapper;
import org.apache.ibatis.annotations.Param;

public interface AlarmDataMapperExt extends AlarmDataMapper {

    AlarmData findActivityUnique(@Param("ip") String ip, @Param("itemName") String itemName, @Param("portCode") String portCode);
}