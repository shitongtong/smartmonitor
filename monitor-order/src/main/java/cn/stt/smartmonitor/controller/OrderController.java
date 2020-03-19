package cn.stt.smartmonitor.controller;


import cn.stt.common.response.Response;
import cn.stt.smartmonitor.dto.AlarmDataDto;
import cn.stt.smartmonitor.enums.AlarmDataEnum;
import cn.stt.smartmonitor.service.AlarmDataHandleServiceFactory;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @ClassName OrderController
 * @Description TODO
 * @Author shitt7
 * @Date 2019/9/2 13:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 处理告警数据
     *
     * @param jsonData
     * @return
     */
    @PostMapping("/handAlarmData")
    public ResponseEntity<Response> handAlarmData(@RequestBody String jsonData) {
        executorService.execute(() -> {
            AlarmDataDto alarmDataDto = JSON.parseObject(jsonData, AlarmDataDto.class);
            AlarmDataEnum.AlarmType alarmTypeEnum = AlarmDataEnum.AlarmType.getEnumByKey(alarmDataDto.getAlarmType());
            if (alarmTypeEnum != null) {
                AlarmDataHandleServiceFactory.getAlarmDataHandleService(alarmTypeEnum)
                        .handAlarmData(alarmDataDto);
            } else {
                LOGGER.info("不存在的alarmType:{}，不处理！", alarmDataDto.getAlarmType());
            }
        });
        return ResponseEntity.ok(Response.success());
    }
}
