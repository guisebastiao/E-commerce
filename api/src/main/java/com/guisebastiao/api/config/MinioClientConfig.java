package com.guisebastiao.api.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {

    @Value("${minio.client.endpoint}")
    private String minioEndpoint;

    @Value("${minio.client.user}")
    private String minioUser;

    @Value("${minio.client.pass}")
    private String minioPass;

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioUser, minioPass)
                .build();
    }
}
