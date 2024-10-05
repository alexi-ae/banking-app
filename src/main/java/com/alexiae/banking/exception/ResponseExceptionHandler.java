package com.alexiae.banking.exception;

import com.alexiae.banking.component.Variables;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  String mensaje = "";

  @Autowired
  private Variables var;

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponseCustom> runtimeException(RuntimeException ex,
      WebRequest request) {
    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), "", "");
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponseCustom> manejarTodasExcepciones(Exception ex,
      WebRequest request) {

    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), var.GENERIC_COD,
        var.GENERIC_MSG);
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ModelNotFoundException.class)
  public final ResponseEntity<ExceptionResponseCustom> manejarModeloException(
      ModelNotFoundException ex, WebRequest request) {
    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.NOT_FOUND), ex.getMessage(), var.MODEL_NOT_FOUND_COD,
        var.MODEL_NOT_FOUND_MSG);
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserDuplicateException.class)
  public final ResponseEntity<ExceptionResponseCustom> userDuplicateException(
      UserDuplicateException ex, WebRequest request) {
    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.BAD_REQUEST), ex.getMessage(), var.USER_DUPLICATE,
        var.USER_DUPLICATE_MSG);
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<ExceptionResponseCustom> IllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {

    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), "", "");
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NumberFormatException.class)
  public final ResponseEntity<ExceptionResponseCustom> NumberFormatException(
      NumberFormatException ex, WebRequest request) {

    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), "", "");
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<ExceptionResponseCustom> MethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), var.BAD_ARGUMENT_COD,
        var.BAD_ARGUMENT_MSG);
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
  public final ResponseEntity<ExceptionResponseCustom> BadCredentialsException(
      org.springframework.security.authentication.BadCredentialsException ex, WebRequest request) {

    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), "", "");
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionResponseCustom> UsernameNotFoundException(
      UsernameNotFoundException ex, WebRequest request) {

    ExceptionResponseCustom exc = new ExceptionResponseCustom(LocalDateTime.now(),
        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage(), "", "");
    return new ResponseEntity<ExceptionResponseCustom>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
