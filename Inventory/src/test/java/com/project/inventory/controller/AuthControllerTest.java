package com.project.inventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.inventory.entity.Erole;
import com.project.inventory.entity.Role;
import com.project.inventory.entity.Users;
import com.project.inventory.repository.RoleRepo;
import com.project.inventory.repository.UserRepo;
import com.project.inventory.request.payload.LoginRequest;
import com.project.inventory.request.payload.SignupRequest;
import com.project.inventory.request.reponse.JwtResponse;
import com.project.inventory.request.reponse.MessageResponse;
import com.project.inventory.security.jwt.JwtUtils;
import com.project.inventory.security.service.UserDetailsImpl;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;



class AuthControllerTest {
  @Mock
  private AuthenticationManager authManager;

  @Mock
  private UserRepo userRepository;

  @Mock
  private RoleRepo roleRepository;

  @Mock
  private PasswordEncoder encoder;

  @Mock
  private JwtUtils jwtUtils;

  @InjectMocks
  private AuthController authController;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void authenticateUser_WithValidCredentials_ShouldReturnJwtResponse() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("jayamadhuri@walmart.com");
    loginRequest.setPassword("Madhu@123");
    Authentication authentication = mock(Authentication.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Collection<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getAuthorities()).thenAnswer((Answer<Collection<? extends GrantedAuthority>>)
                                                   invocation -> authorities);
    when(userDetails.getEmail()).thenReturn("jayamadhuri@walmart.com");
    when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwt-token");
    when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                  .thenReturn(authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    ResponseEntity<?> response = authController.authenticateUser(loginRequest);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    JwtResponse jwtResponse = (JwtResponse) response.getBody();
    assertEquals("jwt-token", jwtResponse.getToken());
    assertEquals("jayamadhuri@walmart.com", jwtResponse.getEmail());
    assertEquals(Collections.singletonList("ROLE_EMPLOYEE"), jwtResponse.getRoles());
    verify(jwtUtils, times(1)).generateJwtToken(any(Authentication.class));
  }
  
  @Test
  void registerUser_WithValidRequest_ShouldReturnSuccessMessageRoleEmployee() {
    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setEmail("test@example.com");
    signUpRequest.setPassword("password");
    signUpRequest.setFirstName("John");
    signUpRequest.setLastName("Doe");
    signUpRequest.setDateOfBirth(Date.valueOf("2000-01-23"));
    signUpRequest.setAge(22);
    signUpRequest.setMobileNumber(8919827384L);
    when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
    when(roleRepository.findByName(Erole.ROLE_EMPLOYEE)).thenReturn(Optional.of(new Role()));
    when(encoder.encode(signUpRequest.getPassword())).thenReturn("encoded-password");
    ResponseEntity<?> response = authController.registerUser(signUpRequest);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(MessageResponse.class, response.getBody().getClass());
    MessageResponse messageResponse = (MessageResponse) response.getBody();
    assertEquals("User registered successfully!", messageResponse.getMessage());
    verify(userRepository, times(1)).existsByEmail(signUpRequest.getEmail());
    verify(userRepository, times(1)).save(any(Users.class));
    verify(roleRepository, times(1)).findByName(Erole.ROLE_EMPLOYEE);
    verify(encoder, times(1)).encode(signUpRequest.getPassword());
  } 
  
  @Test
  void registerUser_WithValidRequest_ShouldReturnSuccessMessageRoleNotGiven() {
    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setEmail("test@example.com");
    signUpRequest.setPassword("password");
    signUpRequest.setFirstName("John");
    signUpRequest.setLastName("Doe");
    signUpRequest.setDateOfBirth(Date.valueOf("2000-01-23"));
    signUpRequest.setAge(22);
    signUpRequest.setMobileNumber(8919827384L);
    Set<String> roles = new HashSet<>();
    roles.add("user");
    signUpRequest.setRole(roles);
    when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
    when(roleRepository.findByName(Erole.ROLE_EMPLOYEE)).thenReturn(Optional.of(new Role()));
    when(encoder.encode(signUpRequest.getPassword())).thenReturn("encoded-password");
    ResponseEntity<?> response = authController.registerUser(signUpRequest);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(userRepository, times(1)).existsByEmail(signUpRequest.getEmail());
    verify(userRepository, times(1)).save(any(Users.class));
    verify(encoder, times(1)).encode(signUpRequest.getPassword());
  }
  
  @Test
  void registerUser_WithValidRequest_ShouldReturnSuccessMessageRoleAdmin() {
    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setEmail("test@example.com");
    signUpRequest.setPassword("password");
    signUpRequest.setFirstName("John");
    signUpRequest.setLastName("Doe");
    signUpRequest.setDateOfBirth(Date.valueOf("2000-01-23"));
    signUpRequest.setAge(22);
    signUpRequest.setMobileNumber(8919827384L);
    Set<String> roles = new HashSet<>();
    roles.add("admin");
    signUpRequest.setRole(roles);
    when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
    when(roleRepository.findByName(Erole.ROLE_ADMIN)).thenReturn(Optional.of(new Role()));
    when(encoder.encode(signUpRequest.getPassword())).thenReturn("encoded-password");
    ResponseEntity<?> response = authController.registerUser(signUpRequest);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(userRepository, times(1)).existsByEmail(signUpRequest.getEmail());
    verify(userRepository, times(1)).save(any(Users.class));
    verify(roleRepository, times(1)).findByName(Erole.ROLE_ADMIN);
    verify(encoder, times(1)).encode(signUpRequest.getPassword());
  }
  
  @Test
  void registerUser_WithExistingEmail_ShouldReturnBadRequest() {
    SignupRequest signUpRequest = new SignupRequest();
    signUpRequest.setEmail("madhuri@walmart.com");
    signUpRequest.setPassword("password");
    signUpRequest.setFirstName("John");
    signUpRequest.setLastName("Doe");
    signUpRequest.setDateOfBirth(Date.valueOf("2000-01-23"));
    signUpRequest.setAge(22);
    signUpRequest.setMobileNumber(8919827384L);
    when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
    ResponseEntity<?> response = authController.registerUser(signUpRequest);   
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(userRepository, times(1)).existsByEmail(signUpRequest.getEmail());
    verify(userRepository, never()).save(any(Users.class));
    verify(roleRepository, never()).findByName(any(Erole.class));
    verify(encoder, never()).encode(anyString());
  }
}
