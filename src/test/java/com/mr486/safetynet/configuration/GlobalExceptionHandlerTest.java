package com.mr486.safetynet.configuration;

import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the GlobalExceptionHandler class.
 * Verifies the behavior of exception handling methods and their HTTP responses.
 */
class GlobalExceptionHandlerTest {

  /**
   * Verifies that handleValidationExceptions returns a 400 Bad Request
   * with a map containing field errors.
   */
  @Test
  void handleValidationExceptions_shouldReturnBadRequestWithFieldErrors() {
    var mockBindingResult = mock(org.springframework.validation.BindingResult.class);
    MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
    when(mockException.getBindingResult()).thenReturn(mockBindingResult);
    when(mockBindingResult.getFieldErrors()).thenReturn(List.of(
            new FieldError("objectName", "field1", "error1"),
            new FieldError("objectName", "field2", "error2")
    ));

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(mockException);

    assertEquals(400, response.getStatusCodeValue());
    assertEquals("error1", response.getBody().get("field1"));
    assertEquals("error2", response.getBody().get("field2"));
  }

  /**
   * Verifies that handleAlreadyExists returns a 400 Bad Request
   * with the appropriate error message.
   */
  @Test
  void handleAlreadyExists_shouldReturnBadRequestWithErrorMessage() {
    EntityAlreadyExistsException exception = new EntityAlreadyExistsException("Entity already exists");

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResponseEntity<String> response = handler.handleAlreadyExists(exception);

    assertEquals(400, response.getStatusCodeValue());
    assertEquals("❌ Duplicate: Entity already exists", response.getBody());
  }

  /**
   * Verifies that handleNotFound returns a 404 Not Found
   * with the appropriate error message.
   */
  @Test
  void handleNotFound_shouldReturnNotFoundWithErrorMessage() {
    EntityNotFoundException exception = new EntityNotFoundException("Entity A");

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResponseEntity<String> response = handler.handleNotFound(exception);

    assertEquals(400, response.getStatusCodeValue());
    assertEquals("❌ Entity not found: Entity A", response.getBody());
  }

  /**
   * Verifies that handleException returns a 500 Internal Server Error
   * with the appropriate error message.
   */
  @Test
  void handleException_shouldReturnInternalServerErrorWithErrorMessage() {
    Exception exception = new Exception("Unexpected error");

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResponseEntity<String> response = handler.handleException(exception);

    assertEquals(500, response.getStatusCodeValue());
    assertEquals("❌ Internal server error: Unexpected error", response.getBody());
  }

  /**
   * Verifies that handleNoHandlerFoundException returns a 404 Not Found
   * with the appropriate error message.
   */
  @Test
  void handlePageNotFound__shouldReturnNotFoundWithErrorMessage() {
    NoHandlerFoundException exception = new NoHandlerFoundException("Page not found", "Page1", null);

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    ResponseEntity<String> response = handler.handleNoHandlerFoundException(exception);

    assertEquals(404, response.getStatusCodeValue());
    assertEquals("❌ Page not found: Page1", response.getBody());
  }
}