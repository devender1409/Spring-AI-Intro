package com.callmedev.spring_ai.dto.response;

import com.callmedev.spring_ai.model.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @JsonProperty("status")
    private final String status;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("data")
    private final T data;

    @JsonProperty("error_code")
    private final String errorCode;

    @JsonProperty("errors")
    private final List<ErrorDetail> errors;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    private ApiResponse(String status, String message, T data, String errorCode, List<ErrorDetail> errors, Instant timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("success", message, data, null, null, Instant.now());
    }

    public static <T> ApiResponse<T> error(String errorCode, String message, List<ErrorDetail> errors) {
        return new ApiResponse<>("error", message, null, errorCode, errors, Instant.now());
    }

    public static <T> ApiResponse<T> failure(String errorCode, String message, List<ErrorDetail> errors) {
        return new ApiResponse<>("error", message, null, errorCode, errors, Instant.now());
    }

    public static <T> ApiResponse<T> failure(String errorCode, String message) {
        return new ApiResponse<>("error", message, null, errorCode, null, Instant.now());
    }
}

