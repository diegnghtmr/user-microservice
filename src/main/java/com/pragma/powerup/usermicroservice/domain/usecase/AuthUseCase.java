package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IAuthServicePort;
import com.pragma.powerup.usermicroservice.domain.exception.AuthenticationFailedException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenProviderPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;

public class AuthUseCase implements IAuthServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final ITokenProviderPort tokenProviderPort;

    public AuthUseCase(IUserPersistencePort userPersistencePort,
                       IPasswordEncoderPort passwordEncoderPort,
                       ITokenProviderPort tokenProviderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public String login(String email, String password) {
        User user = userPersistencePort.getUserByEmail(email);
        
        if (user == null) {
            throw new AuthenticationFailedException();
        }

        if (!passwordEncoderPort.checkPassword(password, user.getPassword())) {
            throw new AuthenticationFailedException();
        }

        return tokenProviderPort.generateToken(user);
    }
}
