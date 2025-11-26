package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {
    User saveUser(User user);

    User getUserByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();
}
