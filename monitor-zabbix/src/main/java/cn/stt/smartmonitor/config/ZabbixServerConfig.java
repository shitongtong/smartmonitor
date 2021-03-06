package cn.stt.smartmonitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author shitt7
 * @Date 2020/3/10 16:12
 */
@Component
@ConfigurationProperties(prefix = "zabbix-server")
@Data
public class ZabbixServerConfig {
    private String url;
    private String user;
    private String password;
}
