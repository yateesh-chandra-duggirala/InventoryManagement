package com.project.inventory.request.payload;

import jakarta.validation.constraints.NotBlank;
/**
 * Login request class.
*/

public class LoginRequest {
  @NotBlank
  private String email;
  @NotBlank
  private String password;
  
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
}