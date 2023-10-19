package com.project.inventory.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.entity.Users;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * userdetails will be checked when login.
*/

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;
  @NotEmpty(message = "Email is required")
  @Pattern(regexp = "^[A-Z0-9a-z+_-]+@walmart[.]com$",  message = "Email is not Valid")
  private String email;
  @NotEmpty(message = "Password is required")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
           message = "Password must be at least 8 characters long and "
                      + "contain at least one digit, one lowercase letter, one uppercase letter, "
                      + "one special character, and no whitespace")
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  /**
   * parameter constructor.
  */
  
  public UserDetailsImpl(String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }
  /**
   * userdetails build method.
  */ 
  
  public static UserDetailsImpl build(Users user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getEmail(),
        user.getPassword(), 
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  
  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(email, user.email);
  }

  @Override
  public String getUsername() {
    return email;
  }

}