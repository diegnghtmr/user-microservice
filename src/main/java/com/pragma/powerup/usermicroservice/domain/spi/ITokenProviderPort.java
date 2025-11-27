package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.User;

public interface ITokenProviderPort {
    String generateToken(User user);
}
