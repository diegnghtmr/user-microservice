package com.pragma.powerup.usermicroservice.infrastructure.output.jpa.repository;

import com.pragma.powerup.usermicroservice.infrastructure.output.jpa.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRoleIgnoreCase(String role);

    List<UserEntity> findByIdRestaurantAndRoleIgnoreCase(Long idRestaurant, String role);
}
