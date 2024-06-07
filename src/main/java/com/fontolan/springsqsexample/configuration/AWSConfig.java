package com.fontolan.springsqsexample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AWSConfig {
    private static final String LOCALSTACK_URL = "http://localhost:4566";
    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create(LOCALSTACK_URL))
                .region(Region.US_EAST_1)  // Altere para sua região
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
