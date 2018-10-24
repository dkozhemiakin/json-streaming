package com.jet.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.jet.config.properties.AwsProperties;
import com.jet.config.properties.S3Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AwsProperties.class, S3Properties.class})
@Slf4j
public class Config {

    @Bean
    public AWSCredentialsProvider getCredentialsProvider() {
        return new ProfileCredentialsProvider("eis-deliverydevqa");
    }

    @Bean
    public AmazonS3 amazonS3(AWSCredentialsProvider credentialsProvider, AwsProperties awsProperties) {
        return AmazonS3Client
                .builder()
                .withRegion(awsProperties.getRegion())
                .withCredentials(credentialsProvider)
                .build();
    }

    @Bean
    public TransferManager transferManager(AmazonS3 amazonS3) {
        return TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3)
                .build();
    }

}
