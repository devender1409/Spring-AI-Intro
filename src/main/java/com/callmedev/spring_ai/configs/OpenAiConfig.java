package com.callmedev.spring_ai.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.ai.openai")
public class OpenAiConfig {

    /**
     * Your OpenAI API key
     */
    private String apiKey;

    /**
     * Base URL for OpenAI API
     */
    private String baseUrl;

    // Getters and Setters
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}



