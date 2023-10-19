package com.project.inventory.security.service;

import static org.mockito.Mockito.mock;

import com.project.inventory.entity.Users;
import com.project.inventory.repository.UserRepo;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTest {
  private UserDetailsServiceImpl userDetailsService;
  private UserRepo userRepository;

  @BeforeEach
  public void setUp() {
    userRepository = mock(UserRepo.class);
    userDetailsService = new UserDetailsServiceImpl(userRepository);
  }
  
  @Test
  public void loadUserByUsername_ValidEmail_UserDetailsReturned() {
    String email = "madhuri@walmart.com";
    Users user = new Users();
    user.setEmail(email);
    Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals(email, userDetails.getUsername());
  }
  
  @Test
  public void loadUserByUsername_InvalidEmail_ThrowsUsernameNotFoundException() {
    String email = "sunitha@walmart.com";
    Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
    Assertions.assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername(email);
    });
  }
    
}
