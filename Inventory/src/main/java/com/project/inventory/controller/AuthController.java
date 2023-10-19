package com.project.inventory.controller;

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
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller class.
*/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  /**
   * authenticationmanager instance used for authentication and authorization.
   */
  @Autowired
  private AuthenticationManager authManager;
  /**
   * userrepository instance used for user details.
   */
  @Autowired
  private UserRepo userRepository;
  /**
   * rolerepo instance used for user roles.
   */
  @Autowired
  private RoleRepo roleRepository;
  /**
   * encoder instance used as password encoder.
   */
  @Autowired
  private PasswordEncoder encoder;
  /**
   * jwtutils instance used for jwt token.
   */
  @Autowired
  private JwtUtils jwtUtils;
  /**
   * login method.
  */
  
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {
    final Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), 
                                                loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String jwt = jwtUtils.generateJwtToken(authentication);
    
    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    final List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getEmail(), 
                         roles));
  }
  /**
   * register method.
  */
  
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {
    
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    // Create new user's account
    final Users user = new Users(signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()),
                              signUpRequest.getFirstName(),
                              signUpRequest.getLastName(),
                              signUpRequest.getDateOfBirth(),
                              signUpRequest.getAge(),
                              signUpRequest.getMobileNumber());

    final Set<String> strRoles = signUpRequest.getRole();
    final Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      final Role userRole = roleRepository.findByName(Erole.ROLE_EMPLOYEE)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            final Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          default:
            final Role userRole = roleRepository.findByName(Erole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}