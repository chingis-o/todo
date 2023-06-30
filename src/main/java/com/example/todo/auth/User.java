package com.example.todo.auth;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "users")
public class User {

  @Id
  private ObjectId id;
  private final String username;
  private final String password;

}
