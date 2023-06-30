package com.example.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {

  @GetMapping("/greeting")
  public ResponseEntity<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return ResponseEntity.ok(name);
  }
}
