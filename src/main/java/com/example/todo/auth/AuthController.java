package com.example.todo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/user/{username}")
  public ResponseEntity<?> getUser(@PathVariable String username) {
    try {
      User user = userRepository.findByUsername(username);

      if (user != null) {
        return ResponseEntity.ok(user);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
  }

  @Validated
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ValidationErrorResponse errorResponse = new ValidationErrorResponse();

      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    try {
      userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
      userService.registerUser(userDto);

      return ResponseEntity.ok("User created successfully");
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }

  }

  // @PostMapping("/login")
  // public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
  //   try {
  //     // Perform authentication
  //     Authentication authentication = authenticationManager.authenticate(
  //         new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

  //     // Generate JWT token
  //     String token = jwtTokenProvider.generateToken(authentication);

  //     // Create JwtResponse containing the token
  //     JwtResponse jwtResponse = new JwtResponse(token);

  //     // Return the token in the response
  //     return ResponseEntity.ok(jwtResponse);
  //   } catch (AuthenticationException e) {
  //     // Handle authentication failure
  //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  //   }
  // }
}
