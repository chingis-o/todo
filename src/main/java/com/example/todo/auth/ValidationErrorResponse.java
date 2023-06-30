package com.example.todo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
  private List<ValidationErrorDetail> errors;

  public void addError(String field, String message) {
    if (errors == null) {
      errors = new ArrayList<>();
    }
    errors.add(new ValidationErrorDetail(field, message));
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ValidationErrorDetail {
    private String field;
    private String message;
  }
}
