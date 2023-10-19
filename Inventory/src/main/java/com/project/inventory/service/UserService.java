package com.project.inventory.service;

import com.project.inventory.entity.Users;
import java.util.List;
import java.util.Optional;
/**
 * users service interface.
*/

public interface UserService {

  Users updateUser(Long id, Users users);

  List<Users> getUsers();

  List<Users> getEmployees();

  Optional<Users> findUserById(Long id);

  void deleteUser(Long id);
}
