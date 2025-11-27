package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.application.dto.response.AuthResponseDto; // Wait, domain cannot depend on application DTOs.
// The domain should return a domain object or String.
// The request says: "Retornar un objeto de dominio AuthToken o String".
// So I will return String (the token).

public interface IAuthServicePort {
    String login(String email, String password);
}
