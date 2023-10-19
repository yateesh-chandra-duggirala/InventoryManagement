package com.project.inventory.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.inventory.entity.Erole;
import com.project.inventory.entity.InventoryItems;
import com.project.inventory.entity.Role;
import com.project.inventory.entity.Users;
import com.project.inventory.repository.EmployeeItemsRepo;
import com.project.inventory.repository.ItemRepo;
import com.project.inventory.repository.UserRepo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserServiceImplTest {
  private UserServiceImpl userServiceImpl;
  private UserRepo userRepo;
  private ItemRepo itemRepo;
  private EmployeeItemsRepo empItemRepo;
  
  @BeforeEach
  void setUp() {
    userRepo = mock(UserRepo.class);
    itemRepo = mock(ItemRepo.class);
    empItemRepo = mock(EmployeeItemsRepo.class);
    userServiceImpl = new UserServiceImpl(userRepo, itemRepo, empItemRepo);
  }

  @Test
  void testUpdateUserValidId() {
    Long userId = 68L;
    Users existingUser = new Users("madhuri@walmart.com", "Madhu@123", "JayaMadhuri", "Kaki",
                                    Date.valueOf("2001-01-23"), 22, 8919758066L);
    Users updatedUser = new Users("madhuri@walmart.com", "Madhu@123", "Jaya", "Kaki",
                                   Date.valueOf("2001-01-23"), 22, 8919758066L);
    when(userRepo.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepo.save(existingUser)).thenReturn(updatedUser);
    Users result = userServiceImpl.updateUser(userId, updatedUser);
    assertNotNull(result);
    assertEquals(updatedUser.getFirstName(), result.getFirstName());
  }
  
  @Test
  void testUpdateUserNotValid() {
    Long userId = 1L;
    Users nonExistingUser = new Users("madhuri@walmart.com", "Madhu@123", "JayaMadhuri", "Kaki",
                                       Date.valueOf("2001-01-23"), 22, 8919758066L);
    when(userRepo.findById(userId)).thenReturn(Optional.empty());
    Users result = userServiceImpl.updateUser(userId, nonExistingUser);
    assertNull(result);
  }

  @Test
  void testGetUsersIfExist() {
    List<Users> repoResult = userRepo.findAll();
    assertNotNull(repoResult);
    List<Users> result = userServiceImpl.getUsers();
    assertNotNull(result);
    assertEquals(repoResult, result);
  }
  
  @Test
  void testFindUserByIdIfExist() {
    Long userId = 68L;
    Users user = new Users("madhuri@walmart", "Madhu@123", "JayaMadhuri", "Kaki",
                           Date.valueOf("2001-01-23"), 22, 8919758066L);
    when(userRepo.findById(userId)).thenReturn(Optional.of(user));
    Optional<Users> result = userServiceImpl.findUserById(userId);
    assertTrue(result.isPresent());
    assertEquals(user.getEmail(), result.get().getEmail());
  }
  
  @Test
  void testFindUserByIdIfNotExist() {
    Long userId = 1L;
    when(userRepo.findById(userId)).thenReturn(Optional.empty());
    Optional<Users> result = userServiceImpl.findUserById(userId);
    assertFalse(result.isPresent());
  }

  @Test
  void testFindUserByEmail() {
    String email = "madhuri@walmart.com";
    Users user = new Users("madhuri@walmart", "Madhu@123", "JayaMadhuri", "Kaki",
                            Date.valueOf("2001-01-23"), 22, 8919758066L);
    when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
    Optional<Users> result = userServiceImpl.findUserByEmail(email);
    assertTrue(result.isPresent());
    assertEquals(user.getFirstName(), result.get().getFirstName());
  }
  
  @Test
  public void testDeleteIfExistUserId() {
    Long userId = 68L;
    Users user = new Users("madhuri@walmart.com", "Madhu@123", "JayaMadhuri", "Kaki",
                            Date.valueOf("2001-01-23"), 22, 8919758066L);
    when(userRepo.findById(userId)).thenReturn(Optional.of(user));
    List<InventoryItems> inventoryItems = new ArrayList<>();
    InventoryItems item = new InventoryItems();
    item.setEmpId(userId);
    inventoryItems.add(item);
    when(itemRepo.findByEmpId(userId)).thenReturn(inventoryItems);
    userServiceImpl.deleteUser(userId);
    verify(userRepo, times(1)).deleteById(userId);
    verify(itemRepo, times(1)).save(item);
    verify(empItemRepo, times(1)).deleteByEmpId(userId);
  }
  
  @Test
  public void testDeleteIfNotExistUserId() {
    Long userId = 1L;
    when(userRepo.existsById(userId)).thenReturn(false);
    userServiceImpl.deleteUser(userId);
    verify(itemRepo, never()).save(any(InventoryItems.class));
  }
  
  @Test
  public void testGetEmployees() {
    Role employee = new Role(Erole.ROLE_EMPLOYEE);
    Users user1 = new Users();
    user1.setPassword("Madhuri@123");
    user1.setRoles(Collections.singleton(employee));
    Users user2 = new Users();
    user2.setPassword("Madhu@123");
    user2.setRoles(Collections.singleton(employee));
    List<Users> allUsers = new ArrayList<>();
    allUsers.add(user1);
    allUsers.add(user2);
    when(userRepo.findAll()).thenReturn(allUsers);
    List<Users> result = userServiceImpl.getEmployees();
    assertEquals(2, result.size());
    assertEquals("Madhuri@123", result.get(0).getPassword());
    assertEquals("Madhu@123", result.get(1).getPassword());
  }

}
