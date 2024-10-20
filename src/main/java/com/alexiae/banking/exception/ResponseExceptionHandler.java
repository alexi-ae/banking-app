package com.alexiae.banking.exception;

import io.jsonwebtoken.ExpiredJwtException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ApiRestException.class})
  public ResponseEntity<Object> handleApiRestException(ApiRestException ex, WebRequest request) {
    return buildResponseEntity(
        ErrorResponse.builder()
            .code(ex.getReason().getErrorCode())
            .reason(ex.getReason())
            .source(ex.getSource())
            .errors(
                ex.getMessage() == null
                    ? Collections.emptyList()
                    : Collections.singletonList(ex.getMessage()))
            .build(),
        ex.getReason().getHttpStatus(),
        request);
  }

  @ExceptionHandler({ExpiredJwtException.class})
  public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex,
      WebRequest request) {
    return buildResponseEntity(
        ErrorResponse.builder()
            .code(ErrorReason.UNAUTHORIZED.getErrorCode())
            .reason(ErrorReason.UNAUTHORIZED)
            .source(ErrorSource.BUSINESS_SERVICE)
            .errors(
                ex.getMessage() == null
                    ? Collections.emptyList()
                    : Collections.singletonList(ex.getMessage()))
            .build(),
        ErrorReason.UNAUTHORIZED.getHttpStatus(),
        request);
  }

  private ResponseEntity<Object> buildResponseEntity(
      ErrorResponse errorResponse, HttpStatus status, WebRequest request) {
    return new ResponseEntity<>(errorResponse, status);
  }
}
