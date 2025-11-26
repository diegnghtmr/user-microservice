package com.pragma.powerup.usermicroservice.infrastructure.output.jpa.mapper;

import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {
    UserEntity toEntity(User user);

    User toModel(UserEntity userEntity);

    List<User> toModelList(List<UserEntity> userEntities);
}
