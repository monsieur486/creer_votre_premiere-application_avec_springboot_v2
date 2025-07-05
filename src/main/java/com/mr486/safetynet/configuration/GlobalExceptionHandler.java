package com.mr486.safetynet.configuration;

import com.mr486.safetynet.expetion.EntityAlreadyExistsException;
import com.mr486.safetynet.expetion.EntityNotFoundException;
import com.mr486.safetynet.tools.network.ResponseUtil;
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
  public ResponseEntity<String> handleAlreadyExists(EntityAlreadyExistsException ex) {
    return ResponseUtil.badRequest(
            "❌ Duplicate: " + ex.getMessage()
    );
  }

  /**
   * Handles EntityNotFoundException.
   * Returns an HTTP 404 (Not Found) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
    return ResponseUtil.notFound(
            "Entity not found: " + ex.getMessage()
    );
  }

  /**
   * Handles generic exceptions (Exception).
   * Returns an HTTP 500 (Internal Server Error) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseUtil.internalServerError(
            "Internal server error: " + ex.getMessage()
    );
  }
}