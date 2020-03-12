package cn.stt.smartmonitor.task;

import cn.stt.smartmonitor.service.MonitorZabbixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/10 16:32
 */
@Component
@EnableScheduling
public class MonitorZabbixTask {

    @Autowired
    private MonitorZabbixService monitorZabbixService;

    /**
     * 每15分钟执行: 0 0/15 * * * ?
     * 每十秒执行:  0/10 * * * * ?
     * 每天凌晨1:00点执行 0 0 1 * * ?
     */
    /**
     * 从zabbix获取实时监控数据
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void gatherTask() {
        monitorZabbixService.gatherData();
    }
}
