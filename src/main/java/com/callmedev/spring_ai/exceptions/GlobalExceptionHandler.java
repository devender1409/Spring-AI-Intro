package com.callmedev.spring_ai.exceptions;

import com.callmedev.spring_ai.constants.ErrorCode;
import com.callmedev.spring_ai.dto.response.ApiResponse;
import com.callmedev.spring_ai.model.ErrorDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ErrorDetail(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiResponse<String> response = ApiResponse.failure(
                ErrorCode.VALIDATION_FAILED,
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpClientError(HttpClientErrorException ex) {
        // Wrap the raw message in ErrorDetail
        ErrorDetail errorDetail = new ErrorDetail(
                "openai",
                parseOpenAiMessage(ex.getResponseBodyAsString()) // extract only the "message" field
        );


        ApiResponse<String> response = ApiResponse.failure(
                ErrorCode.OPEN_API_ERROR,
                "OpenAI API call failed: " + ex.getStatusText(),
                List.of(errorDetail)
        );

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }


    // Generic fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        ApiResponse<String> response = ApiResponse.failure(
                ErrorCode.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



    private String parseOpenAiMessage(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            JsonNode errorNode = root.path("error");
            if (!errorNode.isMissingNode()) {
                return errorNode.path("message").asText("Unknown OpenAI error");
            }
        } catch (Exception e) {
            // fallback if parsing fails
            return responseBody;
        }
        return responseBody;
    }

}
