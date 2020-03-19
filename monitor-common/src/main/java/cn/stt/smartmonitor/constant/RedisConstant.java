package cn.stt.smartmonitor.constant;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/17 16:28
 */
public class RedisConstant {

    //-----------------------------同步资源------------------------------//
    /**
     * 同步资源系统资源保存redis前缀key
     */
    private static final String RESOURCE_KEY = "smartmonitor-resource:";
    /**
     * 操作系统类(zabbix服务器采集)信息redisKey
     */
    public static final String FORTSERVERPO_REDISKEY = RESOURCE_KEY + "fortServerPos";

    /**
     * 智能监控redis前缀key
     */
    private static final String SMARTMONITOR_KEY = "smartmonitor:";
    /**
     * 检测到告警连续次数: key + item_name + ip + port(有的话)
     */
    public static final String ALARM_COUNT_KEY = SMARTMONITOR_KEY + "alarmCount:";
    /**
     * 检测到清除告警连续次数: key + item_name + ip + port(有的话)
     */
    public static final String CLEAR_COUNT_KEY = SMARTMONITOR_KEY + "clearCount:";
}
