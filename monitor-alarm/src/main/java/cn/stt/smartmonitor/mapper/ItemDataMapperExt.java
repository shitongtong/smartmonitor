package cn.stt.smartmonitor.mapper;

import cn.stt.smartmonitor.entity.ItemData;
import cn.stt.smartmonitor.mapper.base.ItemDataMapper;
import org.apache.ibatis.annotations.Param;

public interface ItemDataMapperExt extends ItemDataMapper {

    ItemData findUnique(@Param("ip") String ip, @Param("name") String name, @Param("port") String port);
}