package com.callmedev.spring_ai.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShootQuestion {

    @NotNull(message = "Query must not be null")
    private String query;
}

