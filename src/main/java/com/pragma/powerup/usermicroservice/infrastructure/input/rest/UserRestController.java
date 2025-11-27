package com.pragma.powerup.usermicroservice.infrastructure.input.rest;

import com.pragma.powerup.usermicroservice.application.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.application.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "User management endpoints")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
        summary = "Create a new owner",
        responses = {
            @ApiResponse(responseCode = "201", description = "Owner created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
            @ApiResponse(responseCode = "409", description = "User already exists")
        }
    )
    @PostMapping("/owner")
    public ResponseEntity<UserResponseDto> saveOwner(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
        summary = "Create a new employee",
        description = "Only users with role OWNER can create employees.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Employee created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "User already exists")
        }
    )
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
    @PostMapping("/employee")
    public ResponseEntity<UserResponseDto> saveEmployee(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userHandler.saveEmployee(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
        summary = "Register a new client",
        description = "Public endpoint for new clients to register.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Client created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
            @ApiResponse(responseCode = "409", description = "User already exists")
        }
    )
    @PostMapping("/client")
    public ResponseEntity<UserResponseDto> saveClient(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userHandler.saveClient(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
        summary = "Get a user by id",
        responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getUser(id));
    }

    @Operation(
        summary = "List all users",
        responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved")
        }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        return ResponseEntity.ok(userHandler.listUsers());
    }
}
