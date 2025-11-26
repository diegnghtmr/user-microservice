package com.pragma.powerup.usermicroservice.application.mapper;

import com.pragma.powerup.usermicroservice.application.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toModel(UserRequestDto userRequestDto);
}
