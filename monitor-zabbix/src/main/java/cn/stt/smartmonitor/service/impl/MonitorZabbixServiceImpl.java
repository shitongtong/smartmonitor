package cn.stt.smartmonitor.service.impl;

import cn.stt.smartmonitor.config.ZabbixServerConfig;
import cn.stt.smartmonitor.dto.ItemPerformanceDto;
import cn.stt.smartmonitor.entity.AlarmGroup;
import cn.stt.smartmonitor.mapper.AlarmApplicationMapperExt;
import cn.stt.smartmonitor.mapper.AlarmGroupMapperExt;
import cn.stt.smartmonitor.request.ZabbixRequest;
import cn.stt.smartmonitor.request.ZabbixRequestBuilder;
import cn.stt.smartmonitor.service.MonitorZabbixService;
import cn.stt.smartmonitor.util.ZabbixApiUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/10 16:31
 */
@Service
@Slf4j
public class MonitorZabbixServiceImpl implements MonitorZabbixService {

    private ZabbixApiUtil zabbixApiUtil;

    @Autowired
    private ZabbixServerConfig zabbixServerConfig;
    @Autowired
    private AlarmGroupMapperExt alarmGroupMapperExt;
    @Autowired
    private AlarmApplicationMapperExt alarmApplicationMapperExt;

    @Override
    public void gatherData() {
        //login
        zabbixApiUtil = new ZabbixApiUtil(zabbixServerConfig.getUrl());
        zabbixApiUtil.init();
        boolean login = zabbixApiUtil.login(zabbixServerConfig.getUser(), zabbixServerConfig.getPassword());
        if (login) {
            //获取需要采集监控项主机所属的groupId
            List<AlarmGroup> alarmGroupList = alarmGroupMapperExt.findAll();
            for (AlarmGroup alarmGroup : alarmGroupList) {
                String groupId = alarmGroup.getGroupId();
                if (StringUtils.isBlank(groupId)) {
                    //根据groupName获取groupId(方便适用各种不同环境)
                    groupId = zabbixApiUtil.getGroupIdByName(alarmGroup.getGroupName());
                }
                if (StringUtils.isNotBlank(groupId)) {
                    //通过groupId获取其下主机监控项最新数据
                    getLatestItemDataByGroupId(alarmGroup.getId(), groupId);
                } else {
                    //groupId不存在，更新表
                    alarmGroup.setRemark(alarmGroup + "|未发现其对应的groupId");
                    alarmGroupMapperExt.updateByPrimaryKeySelective(alarmGroup);
                }
            }

        }
    }

    public JSONArray getLatestItemDataByGroupId(int alarmGroupId, String groupId) {
        /*List<String> applicationNameList = new ArrayList<>();
        applicationNameList.add("CPU");
        applicationNameList.add("Filesystem /");
        applicationNameList.add("Filesystem /datastore");
        applicationNameList.add("Memory");*/
        //监控项名称applicationName过滤
        List<String> applicationNameList = alarmApplicationMapperExt.findApplicationNameByAlarmGroupId(alarmGroupId);
        JSONObject itemFilter = new JSONObject();
        itemFilter.put("name", applicationNameList);
        ZabbixRequest request = ZabbixRequestBuilder.newBuilder().method("application.get")
                .paramEntry("groupids", groupId)
                .paramEntry("output", "extend")
                .paramEntry("selectHost", new String[]{"host", "name"})
//                .paramEntry("selectItems", "extend")
                .paramEntry("selectItems", new String[]{"itemid", "name", "value_type", "lastclock", "prevvalue", "status", "units", "key_", "lastvalue"})
                .paramEntry("filter", itemFilter)
                .build();
        JSONObject response = zabbixApiUtil.call(request);
        log.info("application.get.response={}", response);
        if (StringUtils.isNotBlank(response.getString("error"))) {
            return null;
        }else {
            JSONArray resultArray = response.getJSONArray("result");
            for (int i = 0; i < resultArray.size(); i++) {
                ItemPerformanceDto dto = new ItemPerformanceDto();
                Map<String, Object> performance = new HashMap<>();
                JSONObject resultObj = resultArray.getJSONObject(0);
                JSONObject hostObj = resultObj.getJSONObject("host");
                //将name配置成ip
                String ip = hostObj.getString("name");
                dto.setIp(ip);
                //所有的指标项
                JSONArray itemsArray = resultObj.getJSONArray("items");
                //过滤只获取配置的指标项

            }
        }
        return null;
    }
}
