package cn.stt.smartmonitor.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/16 8:52
 */
@Data
public class ZabbixDataDto {
    private String ip;
    private String name;
    private String value;
    private String units;
}
