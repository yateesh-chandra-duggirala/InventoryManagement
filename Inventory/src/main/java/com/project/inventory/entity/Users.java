package com.project.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * users entity class.
*/

@Entity
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  
  @NotEmpty(message = "Email is required")
  @Pattern(regexp = "^[A-Z0-9a-z+_-]+@walmart[.]com$",  message = "Email is not Valid")
  private String email;
  
  @NotEmpty(message = "Password is required")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
           message = "Password must be at least 8 characters long and "
                      + "contain at least one digit, one lowercase letter, one uppercase letter, "
                      + "one special character, and no whitespace")
  private String password;
  
  @NotEmpty(message = "FirstName is required")
  private String firstName;
  
  @NotEmpty(message = "LastName is required")
  private String lastName;
  @NotNull(message = "DateOfBirth is required")
  private Date dateOfBirth;
  
  @NotNull(message = "Age is required")
  @Positive(message = "Age must be a positive number")
  private int age;
  
  @NotNull(message = "Mobile number is required")
  @Positive(message = "Mobile number must be a positive number")
  private Long mobileNumber;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public Users() {
    super();
  }
  /**
   * parameter constructor.
  */

  public Users(String email, String password, String firstName, String lastName,
               Date dateOfBirth, int age, Long mobileNumber) {
    super();
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.age = age;
    this.mobileNumber = mobileNumber;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
 
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Long getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(Long mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
