package cn.stt.smartmonitor.mapper;

import java.util.List;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/11 15:57
 */
public interface AlarmApplicationMapperExt extends AlarmApplicationMapper {

    List<String> findApplicationNameByGroupName(String groupName);
}
