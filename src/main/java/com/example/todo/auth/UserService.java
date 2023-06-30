package com.example.todo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void registerUser(UserDto userDto) {

    if (userRepository.existsByUsername(userDto.getUsername())) {
      throw new UserAlreadyExistsException("User with name " +
          userDto.getUsername() + " already exists");
    }

    User user = new User(userDto.getUsername(), userDto.getPassword());
    userRepository.save(user);
  }

}
