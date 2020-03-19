package cn.stt.smartmonitor.service;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/13 11:22
 */
public interface MonitorAlarmService {

    void handleZabbixData(String zabbixData);
}
