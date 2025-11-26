package com.pragma.powerup.usermicroservice.infrastructure.output.jpa.adapter;

import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.repository.IUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        return userEntityMapper.toModel(userRepository.save(userEntityMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::toModel);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userEntityMapper::toModel);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toModelList(userRepository.findAll());
    }
}
