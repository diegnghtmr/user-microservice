package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exception.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exception.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    void createUserThrowsExceptionWhenEmailExists() {
        User user = buildUser(null);
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.createUser(user));
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void createUserPersistsAndReturnsUser() {
        User user = buildUser(null);
        User persistedUser = buildUser(1L);
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.save(any(User.class))).thenReturn(persistedUser);

        User result = userUseCase.createUser(user);

        assertEquals(persistedUser.getId(), result.getId());
        assertEquals(persistedUser.getEmail(), result.getEmail());
        verify(userPersistencePort).save(any(User.class));
    }

    @Test
    void getUserByIdReturnsUser() {
        User persistedUser = buildUser(1L);
        when(userPersistencePort.findById(anyLong())).thenReturn(Optional.of(persistedUser));

        User result = userUseCase.getUserById(1L);

        assertEquals(persistedUser.getId(), result.getId());
        assertEquals(persistedUser.getEmail(), result.getEmail());
    }

    @Test
    void getUserByIdThrowsWhenNotFound() {
        when(userPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(2L));
    }

    @Test
    void getAllUsersReturnsCollection() {
        List<User> users = List.of(buildUser(1L), buildUser(2L));
        when(userPersistencePort.findAll()).thenReturn(users);

        List<User> result = userUseCase.getAllUsers();

        assertEquals(2, result.size());
    }

    private User buildUser(Long id) {
        return new User(
            id,
            "John",
            "Doe",
            "john.doe@example.com",
            "DOC123",
            "+123456789",
            "SecretPwd1!",
            "ADMIN"
        );
    }
}
