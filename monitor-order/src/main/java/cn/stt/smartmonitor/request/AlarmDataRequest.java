package cn.stt.smartmonitor.request;

import cn.stt.common.request.BaseRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AlarmDataRequest
 * @Description 接收到的告警数据
 * @Author shitt7
 * @Date 2019/1/30 10:22
 * @Version 1.0
 */
@Data
public class AlarmDataRequest extends BaseRequest {
    @NotBlank(message = "alarmId不可为空！")
    private String alarmId;
    @NotNull(message = "alarmStatus不可为空！")
    @Range(min = 0, max = 1, message = "alarmStatus值应为0或1！")
    private Integer alarmStatus;
    @NotNull(message = "alarmLevel不可为空！")
    @Range(min = 0, max = 2, message = "alarmLevel值应为0~2！")
    private Integer alarmLevel;
    @NotNull(message = "sendStatus不可为空！")
    @Range(min = 0, max = 1, message = "sendStatus值应为0或1！")
    private Integer sendStatus;
    @NotNull(message = "alarmType不可为空！")
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
    /**
     * 动环变量ID
     */
    private String mrId;
}
