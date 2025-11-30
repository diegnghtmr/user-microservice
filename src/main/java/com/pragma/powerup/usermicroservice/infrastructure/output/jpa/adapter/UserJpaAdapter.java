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
    public User saveUser(User user) {
        return userEntityMapper.toModel(userRepository.save(userEntityMapper.toEntity(user)));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::toModel).orElse(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userEntityMapper::toModel);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toModelList(userRepository.findAll());
    }

    @Override
    public List<User> findByRole(String role) {
        return userEntityMapper.toModelList(userRepository.findByRoleIgnoreCase(role));
    }

    @Override
    public List<User> findByRestaurantAndRole(Long restaurantId, String role) {
        return userEntityMapper.toModelList(userRepository.findByIdRestaurantAndRoleIgnoreCase(restaurantId, role));
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
