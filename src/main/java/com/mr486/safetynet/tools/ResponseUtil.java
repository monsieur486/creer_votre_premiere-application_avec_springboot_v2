package com.mr486.safetynet.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility class for creating standardized API responses.
 * Provides methods to generate responses with different HTTP statuses.
 */
public class ResponseUtil {

  private ResponseUtil() {
    // Private constructor to prevent instantiation
  }

  /**
   * Creates a success response with HTTP status 200 (OK).
   *
   * @param <T>  the type of the response body
   * @param data the data to include in the response body
   * @return a ResponseEntity containing the data and HTTP status 200
   */
  public static <T> ResponseEntity<T> success(T data) {
    return new ResponseEntity<>(data, HttpStatus.OK);
  }

  /**
   * Creates a response with HTTP status 201 (Created).
   *
   * @param <T>     the type of the response body
   * @param message the message to include in the response body
   * @return a ResponseEntity containing the message and HTTP status 201
   */
  public static <T> ResponseEntity<String> created(String message) {
    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  /**
   * Creates a response with HTTP status 400 (Bad Request).
   *
   * @param message the message to include in the response body
   * @return a ResponseEntity containing the message and HTTP status 400
   */
  public static ResponseEntity<String> badRequest(String message) {
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  /**
   * Creates a response with HTTP status 404 (Not Found).
   *
   * @param message the message to include in the response body
   * @return a ResponseEntity containing the message and HTTP status 404
   */
  public static ResponseEntity<String> notFound(String message) {
    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }

  /**
   * Creates a response with HTTP status 500 (Internal Server Error).
   *
   * @param message the message to include in the response body
   * @return a ResponseEntity containing the message and HTTP status 500
   */
  public static ResponseEntity<String> internalServerError(String message) {
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}