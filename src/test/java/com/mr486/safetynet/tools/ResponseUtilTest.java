package com.mr486.safetynet.tools;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ResponseUtil.
 * Contains unit tests to verify the behavior of utility methods for creating HTTP responses.
 */
class ResponseUtilTest {

  /**
   * Verifies that the success method returns a response with status 200 and the provided data.
   */
  @Test
  void success_shouldReturnResponseWithStatus200AndData() {
    String data = "Success data";
    ResponseEntity<String> response = ResponseUtil.success(data);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(data, response.getBody());
  }

  /**
   * Verifies that the created method returns a response with status 201 and the provided message.
   */
  @Test
  void created_shouldReturnResponseWithStatus201AndMessage() {
    String message = "Resource created";
    ResponseEntity<String> response = ResponseUtil.created(message);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals(message, response.getBody());
  }

  /**
   * Verifies that the badRequest method returns a response with status 400 and the provided message.
   */
  @Test
  void badRequest_shouldReturnResponseWithStatus400AndMessage() {
    String message = "Invalid request";
    ResponseEntity<String> response = ResponseUtil.badRequest(message);

    assertEquals(400, response.getStatusCodeValue());
    assertEquals(message, response.getBody());
  }

  /**
   * Verifies that the notFound method returns a response with status 404 and the provided message.
   */
  @Test
  void notFound_shouldReturnResponseWithStatus404AndMessage() {
    String message = "Resource not found";
    ResponseEntity<String> response = ResponseUtil.notFound(message);

    assertEquals(404, response.getStatusCodeValue());
    assertEquals(message, response.getBody());
  }

  /**
   * Verifies that the internalServerError method returns a response with status 500 and the provided message.
   */
  @Test
  void internalServerError_shouldReturnResponseWithStatus500AndMessage() {
    String message = "Server error";
    ResponseEntity<String> response = ResponseUtil.internalServerError(message);

    assertEquals(500, response.getStatusCodeValue());
    assertEquals(message, response.getBody());
  }
}