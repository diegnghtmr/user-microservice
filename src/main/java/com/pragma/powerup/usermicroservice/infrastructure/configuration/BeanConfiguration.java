package com.pragma.powerup.usermicroservice.infrastructure.configuration;

import com.pragma.powerup.usermicroservice.domain.api.IAuthServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenProviderPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.AuthUseCase;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.usermicroservice.infrastructure.security.BCryptPasswordEncoderAdapter;
import com.pragma.powerup.usermicroservice.infrastructure.security.JwtProviderAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserJpaAdapter userJpaAdapter;

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter();
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return userJpaAdapter;
    }

    @Bean
    public ITokenProviderPort tokenProviderPort() {
        return new JwtProviderAdapter();
    }

    @Bean
    public IUserServicePort userServicePort(IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userJpaAdapter, passwordEncoderPort);
    }

    @Bean
    public IAuthServicePort authServicePort(IPasswordEncoderPort passwordEncoderPort, ITokenProviderPort tokenProviderPort) {
        return new AuthUseCase(userJpaAdapter, passwordEncoderPort, tokenProviderPort);
    }
}