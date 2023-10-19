package com.project.inventory.controller;

import com.project.inventory.entity.Users;
import com.project.inventory.repository.UserRepo;
import com.project.inventory.request.reponse.ResponseHandler;
import com.project.inventory.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Users Controller class.
*/

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
@RestController
public class UserController {
  /**
   * userserviceimpl instance for user services.
  */
  private final UserServiceImpl userServiceImpl;
  /**
   * userrepo instance for userdetails.
  */
  private final UserRepo userRepo;
  /**
   * parameter constructor.
  */
  
  public UserController(final UserServiceImpl userServiceImpl, final UserRepo userRepo) {
    this.userServiceImpl = userServiceImpl;
    this.userRepo = userRepo;
  }
  /**
   * getting employees.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/getemployees")
  public ResponseEntity<Object> getEmployees() {
    try {
      List<Users> allUsers =  userServiceImpl.getEmployees();
      return ResponseHandler.generateResponse("Successfully retrieved data!",
                                               HttpStatus.OK, "employees", allUsers);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employees", null);
    }
  }
  /**
   * updating users.
  */
  
  @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @PutMapping("/updateusers/{empId}")
  public ResponseEntity<Object> updateUser(@PathVariable final Long empId,
                                           @RequestBody final Users users) {
    try {
      Users result = userServiceImpl.updateUser(empId, users);
      return ResponseHandler.generateResponse("Successfully Updated data!",
              HttpStatus.OK, "employees", result); 
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
              HttpStatus.MULTI_STATUS, "employees", null);
    }
    
  }
  /**
   * deleting users.
  */
  
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/deleteusers/{empId}")
  public ResponseEntity<Object> deleteUser(@PathVariable final Long empId) {
    try {
      userServiceImpl.deleteUser(empId);
      return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, "employees", null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employees", null);
    }
  }
  /**
   * getting users by id.
  */
  
  @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @GetMapping("/getusers/{empId}")
  public ResponseEntity<Object> findUserById(@PathVariable final Long empId) {
    try {
      Optional<Users> user = userServiceImpl.findUserById(empId);
      return ResponseHandler.generateResponse("Successfully retrieved data!!",
                                               HttpStatus.OK, "employees", user);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employees", null);
    }
  }
  /**
   * getting users by email.
  */
  
  @GetMapping("/getusersbyemail/{email}")
  public ResponseEntity<Object> findUserByEmail(@PathVariable final String email) {
    try {
      Optional<Users> allUsers = userRepo.findByEmail(email);
      return ResponseHandler.generateResponse("Successfully retrieved data!!",
                                               HttpStatus.OK, "employees", allUsers);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(),
                                              HttpStatus.MULTI_STATUS, "employees", null);
    }
  }
}
