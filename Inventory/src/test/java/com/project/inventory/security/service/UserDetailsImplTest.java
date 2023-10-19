package com.project.inventory.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UserDetailsImplTest {
  @Test
  void testGetAuthorities() {
    Collection<GrantedAuthority> authorities = Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com", 
                                         "password@123", authorities);
    assertEquals(authorities, userDetails.getAuthorities());
  }

  @Test
  void testGetEmail() {
    String email = "test@walmart.com";
    UserDetailsImpl userDetails = new UserDetailsImpl(email, "password@123",
                                   Collections.emptyList());
    assertEquals(email, userDetails.getEmail());
  }

  @Test
  void testGetPassword() {
    String password = "password@123";
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                               password, Collections.emptyList());
    assertEquals(password, userDetails.getPassword());
  }

  @Test
  void testIsAccountNonExpired() {
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                                                  "password@123", Collections.emptyList());
    assertTrue(userDetails.isAccountNonExpired());
  }

  @Test
  void testIsAccountNonLocked() {
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                                   "password@123", Collections.emptyList());
    assertTrue(userDetails.isAccountNonLocked());
  }

  @Test
  void testIsCredentialsNonExpired() {
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                               "password@123", Collections.emptyList());
    assertTrue(userDetails.isCredentialsNonExpired());
  }

  @Test
  void testIsEnabled() {
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                                     "password@123", Collections.emptyList());
    assertTrue(userDetails.isEnabled());
  }

  @Test
  void testSameObject() {
    UserDetailsImpl userDetails = new UserDetailsImpl("test@walmart.com",
                                                   "password@123", Collections.emptyList());
    assertTrue(userDetails.equals(userDetails));
  }
  
  @Test
  void testEqualsObject() {
    UserDetailsImpl userDetails1 = new UserDetailsImpl("test@walmart.com",
                                            "password@123", Collections.emptyList());
    UserDetailsImpl userDetails2 = new UserDetailsImpl("test@walmart.com",
                                             "password@1234", Collections.emptyList());
    assertTrue(userDetails1.equals(userDetails2));
  }
  
  @Test
  void testDifferentObject() {
    UserDetailsImpl userDetails1 = new UserDetailsImpl("test1@walmart.com",
                                                      "password@123", Collections.emptyList());
    UserDetailsImpl userDetails2 = new UserDetailsImpl("test2@walmart.com",
                                                      "password@123", Collections.emptyList());
    assertFalse(userDetails1.equals(userDetails2));
  }
}
