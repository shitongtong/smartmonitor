package cn.stt.smartmonitor.mapper.base;

import cn.stt.smartmonitor.entity.ItemDataHistory;

public interface ItemDataHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDataHistory record);

    int insertSelective(ItemDataHistory record);

    ItemDataHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDataHistory record);

    int updateByPrimaryKey(ItemDataHistory record);
}