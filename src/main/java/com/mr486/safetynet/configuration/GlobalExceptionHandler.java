package com.mr486.safetynet.configuration;

import com.mr486.safetynet.tools.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * Handle IllegalArgumentException and return a custom error response.
   *
   * @param ex the exception
   * @return ResponseEntity with ApiResponse containing error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST,
            "Une erreur est survenue: " + ex.getMessage(),
            null
    );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
