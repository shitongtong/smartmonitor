package cn.stt.smartmonitor.controller;

import cn.stt.common.response.Response;
import cn.stt.smartmonitor.service.MonitorAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/13 11:19
 */
@RestController
@RequestMapping("/")
public class MonitorAlarmController {
    @Autowired
    private MonitorAlarmService monitorAlarmService;

    @PostMapping("/handleZabbixData")
    public ResponseEntity<Response> handleZabbixData(@RequestBody String zabbixData) {
        //zabbixData={"ip":"10.0.4.5","name":"cpu-utilization","units":"%","value":"1.2627"}
        monitorAlarmService.handleZabbixData(zabbixData);
        return ResponseEntity.ok(Response.success());
    }
}
