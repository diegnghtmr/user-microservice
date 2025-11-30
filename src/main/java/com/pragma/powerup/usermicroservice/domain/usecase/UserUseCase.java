package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exception.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exception.UserMustBeAdultException;
import com.pragma.powerup.usermicroservice.domain.exception.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User saveUser(User user) {
        if (user.getBirthDate() == null || Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserMustBeAdultException();
        }

        if (userPersistencePort.getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }

        user.setRole("ROLE_OWNER");
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        return userPersistencePort.saveUser(user);
    }

    @Override
    public User saveEmployee(User user) {
        if (user.getBirthDate() == null || Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserMustBeAdultException();
        }

        if (userPersistencePort.getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }

        user.setRole("ROLE_EMPLOYEE");
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        return userPersistencePort.saveUser(user);
    }

    @Override
    public User saveClient(User user) {
        if (user.getBirthDate() == null || Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserMustBeAdultException();
        }

        if (userPersistencePort.getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }

        user.setRole("ROLE_CLIENT");
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        return userPersistencePort.saveUser(user);
    }

    @Override
    public User saveAdmin(User user) {
        if (user.getBirthDate() == null || Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserMustBeAdultException();
        }

        if (userPersistencePort.getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }

        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        return userPersistencePort.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.findAll();
    }

    @Override
    public List<User> getOwners() {
        return userPersistencePort.findByRole("ROLE_OWNER");
    }

    @Override
    public List<User> getEmployeesByRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant id is required");
        }
        return userPersistencePort.findByRestaurantAndRole(restaurantId, "ROLE_EMPLOYEE");
    }

    @Override
    public boolean existsById(Long id) {
        return userPersistencePort.existsById(id);
    }
}
