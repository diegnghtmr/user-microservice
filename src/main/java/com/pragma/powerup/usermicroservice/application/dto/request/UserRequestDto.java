package com.pragma.powerup.usermicroservice.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "Document number must be numeric")
    private String documentNumber;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = "^\\+?\\d+$", message = "Phone number must be numeric and can contain +")
    private String phone;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private Long idRestaurant;
}
