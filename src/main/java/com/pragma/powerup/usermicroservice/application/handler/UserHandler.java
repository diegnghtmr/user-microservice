package com.pragma.powerup.usermicroservice.application.handler;

import com.pragma.powerup.usermicroservice.application.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.application.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.application.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.application.mapper.IUserResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toModel(userRequestDto);
        User createdUser = userServicePort.saveUser(user);
        return userResponseMapper.toResponse(createdUser);
    }

    @Override
    public UserResponseDto saveEmployee(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toModel(userRequestDto);
        User createdUser = userServicePort.saveEmployee(user);
        return userResponseMapper.toResponse(createdUser);
    }

    @Override
    public UserResponseDto saveClient(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toModel(userRequestDto);
        User createdUser = userServicePort.saveClient(user);
        return userResponseMapper.toResponse(createdUser);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userServicePort.getUserById(id);
        return userResponseMapper.toResponse(user);
    }

    @Override
    public List<UserResponseDto> listUsers() {
        return userResponseMapper.toResponseList(userServicePort.getAllUsers());
    }
}
