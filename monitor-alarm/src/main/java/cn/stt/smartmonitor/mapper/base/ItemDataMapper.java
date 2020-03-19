package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.ItemData;

public interface ItemDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemData record);

    int insertSelective(ItemData record);

    ItemData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemData record);

    int updateByPrimaryKey(ItemData record);
}