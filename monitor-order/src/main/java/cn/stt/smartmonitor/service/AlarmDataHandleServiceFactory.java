package cn.stt.smartmonitor.service;

import cn.stt.smartmonitor.enums.AlarmDataEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AlarmDataHandleServiceFactory
 * @Description TODO
 * @Author shitt7
 * @Date 2019/5/9 15:52
 * @Version 1.0
 */
@Component
public class AlarmDataHandleServiceFactory implements ApplicationContextAware {
    private static Map<AlarmDataEnum.AlarmType, AbstractAlarmDataHandleService> alarmDataHandleServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AbstractAlarmDataHandleService> map = applicationContext.getBeansOfType(AbstractAlarmDataHandleService.class);
        alarmDataHandleServiceMap = new HashMap<>();
        map.forEach((key, value) -> alarmDataHandleServiceMap.put(value.getAlarmType(), value));
    }

    public static <T extends AbstractAlarmDataHandleService> T getAlarmDataHandleService(AlarmDataEnum.AlarmType alarmType) {
        return (T) alarmDataHandleServiceMap.get(alarmType);
    }
}
