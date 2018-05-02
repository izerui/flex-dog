package com.github.izerui.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "qi-niu")
public class FileConfig {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String urlPrefix;
}
