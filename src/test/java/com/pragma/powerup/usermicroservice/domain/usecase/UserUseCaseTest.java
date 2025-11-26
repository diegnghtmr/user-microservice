package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exception.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exception.UserMustBeAdultException;
import com.pragma.powerup.usermicroservice.domain.exception.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import java.time.LocalDate;
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

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Test
    void saveUserThrowsExceptionWhenEmailExists() {
        User user = buildUser(null);
        when(userPersistencePort.getUserByEmail(anyString())).thenReturn(user);

        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.saveUser(user));
        verify(userPersistencePort, never()).saveUser(any(User.class));
    }

    @Test
    void saveUserThrowsExceptionWhenUnderage() {
        User user = buildUser(null);
        user.setBirthDate(LocalDate.now().minusYears(17));

        assertThrows(UserMustBeAdultException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    void saveUserPersistsAndReturnsUser() {
        User user = buildUser(null);
        User persistedUser = buildUser(1L);
        when(userPersistencePort.getUserByEmail(user.getEmail())).thenReturn(null);
        when(userPersistencePort.saveUser(any(User.class))).thenReturn(persistedUser);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPwd");

        User result = userUseCase.saveUser(user);

        assertEquals(persistedUser.getId(), result.getId());
        assertEquals(persistedUser.getEmail(), result.getEmail());
        verify(userPersistencePort).saveUser(any(User.class));
        verify(passwordEncoderPort).encode("SecretPwd1!");
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
            LocalDate.now().minusYears(20),
            "SecretPwd1!",
            "ADMIN"
        );
    }
}