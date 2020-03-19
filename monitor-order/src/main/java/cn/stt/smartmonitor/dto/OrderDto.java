package cn.stt.smartmonitor.dto;


import lombok.Data;

/**
 * @ClassName OrderDto
 * @Description 告警派发工单信息
 * @Author shitt7
 * @Date 2019/4/22 20:22
 * @Version 1.0
 */
@Data
public class OrderDto {
    /**
     * 告警编号
     */
    private String alarmId;
    /**
     * 设备类型: 1:服务器; 2:摄像头; 3:nvr; 4:olt; 5:mdu; 6:交换机;7:嗅探
     */
    private Integer eqpType;
    /**
     * 告警类型:  0:摄像头ping测告警; 1:服务器告警; 2:摄像头视频质量告警; 3:NVR告警; 4:OLT告警; 5:MDU告警; 6:交换机告警
     */
    private Integer alarmType;
    /**
     * 告警时间
     */
    private String alarmTime;
    /**
     * 资产编号
     */
    private String assetNumber;
    /**
     * 故障内容
     */
    private String alarmContent;
    /**
     * 故障类型
     */
    private String faultType;
    /**
     * 劣化类型
     */
    private String crackingType;
    /**
     * 端口编号
     */
    private String portCode;
}
