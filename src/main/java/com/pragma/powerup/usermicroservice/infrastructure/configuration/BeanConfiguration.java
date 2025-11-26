package com.pragma.powerup.usermicroservice.infrastructure.configuration;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.usermicroservice.infrastructure.security.BCryptPasswordEncoderAdapter;
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
    public IUserServicePort userServicePort(IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userJpaAdapter, passwordEncoderPort);
    }
}