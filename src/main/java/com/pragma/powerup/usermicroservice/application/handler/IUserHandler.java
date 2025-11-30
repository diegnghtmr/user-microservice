package com.pragma.powerup.usermicroservice.application.handler;

import com.pragma.powerup.usermicroservice.application.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.application.dto.response.UserResponseDto;
import java.util.List;

public interface IUserHandler {
    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto saveEmployee(UserRequestDto userRequestDto);
    
    UserResponseDto saveClient(UserRequestDto userRequestDto);

    UserResponseDto saveAdmin(UserRequestDto userRequestDto);

    UserResponseDto getUser(Long id);

    List<UserResponseDto> listUsers();

    List<UserResponseDto> listOwners();

    List<UserResponseDto> listEmployeesByRestaurant(Long restaurantId);

    boolean userExists(Long id);
}
