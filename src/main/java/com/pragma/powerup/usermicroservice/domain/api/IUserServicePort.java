package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.User;
import java.util.List;

public interface IUserServicePort {
    User saveUser(User user);
    
    User saveEmployee(User user);
    
    User saveClient(User user);

    User saveAdmin(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    List<User> getOwners();

    List<User> getEmployeesByRestaurant(Long restaurantId);

    boolean existsById(Long id);
}
