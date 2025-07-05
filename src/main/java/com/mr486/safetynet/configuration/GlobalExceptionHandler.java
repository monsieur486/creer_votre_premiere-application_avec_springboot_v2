package com.mr486.safetynet.configuration;

import com.mr486.safetynet.expetion.EntityAlreadyExistsException;
import com.mr486.safetynet.expetion.EntityNotFoundException;
import com.mr486.safetynet.tools.network.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * Provides methods to handle specific and generic exceptions,
 * returning standardized HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles EntityAlreadyExistsException.
   * Returns an HTTP 400 (Bad Request) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(EntityAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<String>> handleAlreadyExists(EntityAlreadyExistsException ex) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST,
            "Bad request: " + ex.getMessage(),
            null
    );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles EntityNotFoundException.
   * Returns an HTTP 404 (Not Found) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleNotFound(EntityNotFoundException ex) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.NOT_FOUND,
            "Not found: " + ex.getMessage(),
            null
    );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles generic exceptions (Exception).
   * Returns an HTTP 500 (Internal Server Error) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
    ApiResponse<String> response = new ApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error: " + ex.getMessage(),
            null
    );
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}