package cn.stt.smartmonitor.dto;

import lombok.Data;

/**
 * @ClassName AlarmDataOriginalDto
 * @Description 接收到的告警原数据
 * @Author shitt7
 * @Date 2019/1/30 10:22
 * @Version 1.0
 */
@Data
public class AlarmDataOriginalDto {
    private String alarmId;
    private Integer alarmStatus;
    private Integer alarmLevel;
    private Integer sendStatus;
    private Integer alarmType;
    private Integer alarmSource;
    private String clearId;
    private String alarmTitle;
    private String alarmText;
    private String eventTime;
    private String clearTime;
    private String ip;
    private String mac;
    /**
     * 故障类型 fault_type
     */
    private String performanceName;
    private String crackingType;
    private String alarmImage;
    private String portCode;
    private String code;
    private String reason;
    private String mrId;
}
