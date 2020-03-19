package cn.stt.smartmonitor.dto;

import lombok.Data;

/**
 * @ClassName AlarmDataFullDto
 * @Description 补全后告警数据
 * @Author shitt7
 * @Date 2019/1/30 10:22
 * @Version 1.0
 */
@Data
public class AlarmDataFullDto {
    private String alarmId;
    private String clearId;
    private Integer alarmType;
    private Integer alarmSource;
    private Integer eqpType;
    private String eqpTypeName;
    private String facilityFrim;
    private String facilityName;
    private String crackingType;
    private String faultType;
    private Integer alarmLevel;
    private String alarmTitle;
    private Integer alarmTitleType;
    private Integer alarmTextType;
    private Integer policeId;
    private String policeStation;
    private String globalCode;
    private String ip;
    private String mac;
    private String eqpCode;
    private String location;
    private String alarmImage;
    private String diagnoseResult;
    /**
     * 告警发生时间 时间字段格式采用：YYYY-MM-DD 24hh:mm:ss
     */
    private String eventTime;
    /**
     * 告警清除时间 时间字段格式采用：YYYY-MM-DD 24hh:mm:ss
     */
    private String clearTime;
    private Integer alarmStatus;
    private String alarmText;
    private Integer sendStatus;
    private String portCode;
    private String assetNumber;
    /**
     * 资源类别:资源库表
     */
    private Integer resType;
    private String resTypeName;
    /**
     * 光缆
     */
    private String code;
    private String reason;
    /**
     * 动环
     */
    private String mrId;
}
