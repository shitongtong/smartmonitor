package cn.stt.smartmonitor.service.impl;

import cn.stt.common.util.HttpUtil;
import cn.stt.smartmonitor.config.YmlMyConfig;
import cn.stt.smartmonitor.config.ZabbixServerConfig;
import cn.stt.smartmonitor.entity.AlarmItem;
import cn.stt.smartmonitor.mapper.AlarmApplicationMapperExt;
import cn.stt.smartmonitor.mapper.AlarmGroupMapperExt;
import cn.stt.smartmonitor.mapper.AlarmItemMapperExt;
import cn.stt.smartmonitor.request.ZabbixRequest;
import cn.stt.smartmonitor.request.ZabbixRequestBuilder;
import cn.stt.smartmonitor.service.MonitorZabbixService;
import cn.stt.smartmonitor.util.ZabbixApiUtil;
import com.alibaba.fastjson.JSON;
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
    @Autowired
    private AlarmItemMapperExt alarmItemMapperExt;
    @Autowired
    private YmlMyConfig ymlMyConfig;

    @Override
    public void gatherData() {
        //login
        zabbixApiUtil = new ZabbixApiUtil(zabbixServerConfig.getUrl());
        zabbixApiUtil.init();
        boolean login = zabbixApiUtil.login(zabbixServerConfig.getUser(), zabbixServerConfig.getPassword());
        if (login) {
            //获取需要采集监控项主机所属的groupId
            List<String> groupNameList = alarmGroupMapperExt.findGroupList();
            for (String groupName : groupNameList) {
                handleLatestItemDataByGroupName(groupName);
            }
        }
    }

    public void handleLatestItemDataByGroupName(String groupName) {
        //根据groupName获取groupId
        String groupId = zabbixApiUtil.getGroupIdByName(groupName);
        if (StringUtils.isBlank(groupId)) {
            log.info("该groupName[{}]在zabbix不存在!", groupName);
            return;
        }
        /*List<String> applicationNameList = new ArrayList<>();
        applicationNameList.add("CPU");
        applicationNameList.add("Filesystem /");
        applicationNameList.add("Filesystem /datastore");
        applicationNameList.add("Memory");*/
        //监控项名称applicationName过滤
        List<String> applicationNameList = alarmApplicationMapperExt.findApplicationNameByGroupName(groupName);
        if (applicationNameList == null || applicationNameList.size() == 0) {
            return;
        }
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
        if (StringUtils.isBlank(response.getString("error"))) {
            JSONArray resultArray = response.getJSONArray("result");
            for (int i = 0; i < resultArray.size(); i++) {
                JSONObject resultObj = resultArray.getJSONObject(i);
                JSONObject hostObj = resultObj.getJSONObject("host");
                //将name配置成ip
                String ip = hostObj.getString("name");
                String applicationName = resultObj.getString("name");
                //获取过滤配置的指标项
                List<AlarmItem> itemList = alarmItemMapperExt.findByApplicationName(applicationName);
                //所有的指标项
                JSONArray itemsArray = resultObj.getJSONArray("items");
                for (int j = 0; j < itemsArray.size(); j++) {
                    JSONObject itemObj = itemsArray.getJSONObject(j);
                    String key = itemObj.getString("key_");
                    String lastvalue = itemObj.getString("lastvalue");
                    String units = itemObj.getString("units");
                    for (AlarmItem alarmItem : itemList) {
                        String itemKey = alarmItem.getItemKey();
                        String itemName = alarmItem.getItemName();
                        if (key.equals(itemKey)) {
                            Map<String, String> map = new HashMap<>();
                            map.put("ip", ip);
                            map.put("name", itemName);
                            map.put("value", lastvalue);
                            map.put("units", units);
                            String parameter = JSON.toJSONString(map);
                            try {
                                log.info("发送参数:{}", parameter);
                                String result = HttpUtil.sendPost(ymlMyConfig.getDataHandUrl(), parameter);
                                log.info("发送结果:{}", result);
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    }
                }
            }
        }
    }
}
