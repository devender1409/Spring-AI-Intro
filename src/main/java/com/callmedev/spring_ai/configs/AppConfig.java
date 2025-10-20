package com.callmedev.spring_ai.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    private final OpenAiConfig openAiConfig;

    public AppConfig(OpenAiConfig openAiConfig) {
        this.openAiConfig = openAiConfig;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Add interceptor to automatically include Authorization header
        ClientHttpRequestInterceptor authInterceptor = (request, body, execution) -> {
            request.getHeaders().setBearerAuth(openAiConfig.getApiKey());
            return execution.execute(request, body);
        };

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(authInterceptor);
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}

