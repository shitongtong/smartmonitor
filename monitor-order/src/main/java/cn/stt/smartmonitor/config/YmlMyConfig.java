package cn.stt.smartmonitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName YmlMyConfig
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/18 9:18
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "my")
@Data
public class YmlMyConfig {
    private String sendOrderUrl;
    private String sendClearOrderUrl;
}
