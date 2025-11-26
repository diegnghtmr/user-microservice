package com.pragma.powerup.usermicroservice.infrastructure.configuration;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.adapter.UserJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserJpaAdapter userJpaAdapter;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return userJpaAdapter;
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort) {
        return new UserUseCase(userPersistencePort);
    }
}
