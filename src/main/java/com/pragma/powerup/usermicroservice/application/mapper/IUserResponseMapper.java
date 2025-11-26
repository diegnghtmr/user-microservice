package com.pragma.powerup.usermicroservice.application.mapper;

import com.pragma.powerup.usermicroservice.application.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {
    UserResponseDto toResponse(User user);

    List<UserResponseDto> toResponseList(List<User> users);
}
