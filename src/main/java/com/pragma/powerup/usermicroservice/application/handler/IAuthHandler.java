package com.pragma.powerup.usermicroservice.application.handler;

import com.pragma.powerup.usermicroservice.application.dto.request.AuthRequestDto;
import com.pragma.powerup.usermicroservice.application.dto.response.AuthResponseDto;

public interface IAuthHandler {
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
