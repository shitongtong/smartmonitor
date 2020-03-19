package cn.stt.smartmonitor.dto;

import lombok.Data;

/**
 * @Author shitt7
 * @Date 2019/1/30 10:22
 */
@Data
public class AlarmDataDto {
    private String alarmId;
    private String clearId;
    private String ip;
    private String itemName;
    private String portCode;
    private String faultType;
    private String crackingType;
    private String alarmTitle;
    private String alarmText;
    private Integer alarmStatus;
    private Integer alarmType;
    private Integer alarmSource;
    private String eventTime;
    private String clearTime;
    private Integer alarmLevel;
    private Integer sendStatus;
    private String alarmImage;

}
