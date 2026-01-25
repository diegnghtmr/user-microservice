package com.pragma.powerup.usermicroservice.application.dto.response;

import java.time.LocalDate;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String role;
    private Long idRestaurant;
    private Instant createdAt;
}
