package com.pragma.powerup.usermicroservice.application.handler;

import com.pragma.powerup.usermicroservice.application.dto.request.AuthRequestDto;
import com.pragma.powerup.usermicroservice.application.dto.response.AuthResponseDto;
import com.pragma.powerup.usermicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        String token = authServicePort.login(authRequestDto.getEmail(), authRequestDto.getPassword());
        return new AuthResponseDto(token);
    }
}
