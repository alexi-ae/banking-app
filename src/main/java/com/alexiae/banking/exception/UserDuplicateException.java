package com.alexiae.banking.exception;

public class UserDuplicateException extends RuntimeException {

  public UserDuplicateException(String message) {
    super(message);
  }
}
