package com.pragma.powerup.usermicroservice.infrastructure.exception;

import com.pragma.powerup.usermicroservice.domain.exception.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exception.UserNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message("Validation failed")
            .errors(errors)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.CONFLICT.value())
            .message(exception.getMessage())
            .errors(Collections.emptyList())
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.CONFLICT.value())
            .message("User data violates constraints")
            .errors(List.of(exception.getMostSpecificCause().getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(exception.getMessage())
            .errors(Collections.emptyList())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(com.pragma.powerup.usermicroservice.domain.exception.UserMustBeAdultException.class)
    public ResponseEntity<ApiError> handleUserMustBeAdult(com.pragma.powerup.usermicroservice.domain.exception.UserMustBeAdultException exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message("User must be an adult")
            .errors(Collections.emptyList())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(com.pragma.powerup.usermicroservice.domain.exception.AuthenticationFailedException.class)
    public ResponseEntity<ApiError> handleAuthenticationFailed(com.pragma.powerup.usermicroservice.domain.exception.AuthenticationFailedException exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("Authentication failed")
            .errors(Collections.emptyList())
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception exception) {
        ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Unexpected error occurred")
            .errors(List.of(exception.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
