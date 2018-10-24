package com.jet.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.aws.s3")
@Data
public class S3Properties {

    private String bucketName;
    private String path;
    private String fileName;
    private String outputFileName;

}
