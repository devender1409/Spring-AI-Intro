package com.callmedev.spring_ai.controller;

import com.callmedev.spring_ai.dto.request.ShootQuestion;
import com.callmedev.spring_ai.dto.response.ApiResponse;
import com.callmedev.spring_ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/my-ai")
public class AiController {

    private final AiService aiService;


    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<String>> chat(@Valid @RequestBody ShootQuestion request) {
        String result = aiService.getChatCompletion(request.getQuery());
        ApiResponse<String> response = ApiResponse.success(result, "AI Response");
        return ResponseEntity.ok(response);
    }

}

