package com.mr486.safetynet.configuration;

import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.exeption.WrongDateFormatException;
import com.mr486.safetynet.tools.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides methods to handle specific and generic exceptions,
 * returning standardized HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles MethodArgumentNotValidException.
   * Returns an HTTP 400 (Bad Request) response with validation error details.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing a map of field errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

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
    return ResponseUtil.badRequest(
            "❌ Entity not found: " + ex.getMessage()
    );
  }

  /**
   * Handles 404 errors when no handler is found for a request.
   * Returns an HTTP 404 (Not Found) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an error message
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    return ResponseUtil.notFound(
            "❌ Page not found: " + ex.getRequestURL()
    );
  }

  /**
   * Handles WrongDateFormatException.
   * Returns an HTTP 400 (Bad Request) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(WrongDateFormatException.class)
  public ResponseEntity<String> handleWrongDateFormatException(WrongDateFormatException ex) {
    return ResponseUtil.badRequest(
            "❌ Wrong date format: " + ex.getMessage()
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
            "❌ Internal server error: " + ex.getMessage()
    );
  }
}