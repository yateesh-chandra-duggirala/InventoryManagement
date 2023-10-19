package com.project.inventory.service.impl;

import com.project.inventory.entity.Erole;
import com.project.inventory.entity.InventoryItems;
import com.project.inventory.entity.Role;
import com.project.inventory.entity.Users;
import com.project.inventory.repository.EmployeeItemsRepo;
import com.project.inventory.repository.ItemRepo;
import com.project.inventory.repository.UserRepo;
import com.project.inventory.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * user service implementation.
*/

@Service
public class UserServiceImpl implements UserService {
  private final UserRepo userRepo;
  private final EmployeeItemsRepo employeeItemsRepo;
  private final ItemRepo itemRepo;
  /**
   * parameter constructor.
  */
  
  public UserServiceImpl(UserRepo userRepo, ItemRepo itemRepo,
              EmployeeItemsRepo employeeItemsRepo) {
    this.userRepo = userRepo;
    this.employeeItemsRepo = employeeItemsRepo;
    this.itemRepo = itemRepo;
  }
  
  @Override
  public Users updateUser(Long id, Users users) {
    Users existingUser = userRepo.findById(id).orElse(null);
    if (existingUser != null) {
      existingUser.setFirstName(users.getFirstName());
      existingUser.setLastName(users.getLastName());
      existingUser.setDateOfBirth(users.getDateOfBirth());
      existingUser.setAge(users.getAge());
      existingUser.setMobileNumber(users.getMobileNumber());
      return userRepo.save(existingUser);
    }
    return existingUser;
  }

  @Override
  public List<Users> getUsers() {
    return userRepo.findAll();
  }

  @Override
  public Optional<Users> findUserById(Long id) {
    return userRepo.findById(id);
  }

  @Override
  public void deleteUser(Long id) {
    userRepo.deleteById(id);
    List<InventoryItems> inventoryItems = itemRepo.findByEmpId(id);
    for (InventoryItems item : inventoryItems) {
      item.setEmpId(null); 
      itemRepo.save(item);
    }
    employeeItemsRepo.deleteByEmpId(id);
  }
  
  @Override
  public List<Users> getEmployees() {
    List<Users> allUsers = getUsers();
    List<Users> employees = new ArrayList<>();
    for (Users user : allUsers) {
      Set<Role> roles = user.getRoles();
      for (Role role : roles) {
        if (role.getName() == Erole.ROLE_EMPLOYEE) {
          employees.add(user);
          break;
        }
      }
    }
    return employees;
  }
  
  public Optional<Users> findUserByEmail(String email) {
    return userRepo.findByEmail(email);
  }
}