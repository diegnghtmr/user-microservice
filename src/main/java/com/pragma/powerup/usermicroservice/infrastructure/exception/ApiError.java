package com.pragma.powerup.usermicroservice.infrastructure.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private List<String> errors;
}
