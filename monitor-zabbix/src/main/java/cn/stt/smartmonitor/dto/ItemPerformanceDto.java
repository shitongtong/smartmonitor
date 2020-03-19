package cn.stt.smartmonitor.dto;

import lombok.Data;

import java.util.Map;

/**
 * @Description 设备和指标性能数据
 * @Author shitt7
 * @Date 2020/3/11 16:56
 */
@Data
public class ItemPerformanceDto {
    /**
     * 设备Ip
     */
    private String ip;
    /**
     * 指标性能数据,key:指标名称 value:指标值
     */
    private Map<String, String> performance;
}
