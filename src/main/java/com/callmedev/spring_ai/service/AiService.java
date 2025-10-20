package com.callmedev.spring_ai.service;

import com.callmedev.spring_ai.configs.OpenAiConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final OpenAiConfig openAiConfig;

    public AiService(RestTemplate restTemplate, OpenAiConfig openAiConfig) {
        this.restTemplate = restTemplate;
        this.openAiConfig = openAiConfig;
    }

    public String getChatCompletion(String prompt) {
        String url = openAiConfig.getBaseUrl() + "/chat/completions";

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );


        // Headers (no auth for now)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Make POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        // Extract AI response
        List choices = (List) response.getBody().get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");
            return (String) message.get("content");
        }

        return "No response from AI";
    }
}
