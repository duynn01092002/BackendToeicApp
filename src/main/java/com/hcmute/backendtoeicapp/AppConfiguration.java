package com.hcmute.backendtoeicapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfiguration {
    @Value("${toeic-resource-directory}")
    private String toeicResourceDirectory;
}
