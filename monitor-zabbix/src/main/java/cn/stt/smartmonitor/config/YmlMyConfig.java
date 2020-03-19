package cn.stt.smartmonitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author altho
 * @date 2019-09-02
 */
@Component
@ConfigurationProperties(prefix = "my")
@Data
public class YmlMyConfig {
    private String dataHandUrl;
}
