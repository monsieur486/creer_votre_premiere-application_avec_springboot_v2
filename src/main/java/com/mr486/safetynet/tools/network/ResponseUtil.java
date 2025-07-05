package com.mr486.safetynet.tools.network;

import org.springframework.http.HttpStatus;

/**
 * Utility class for creating standardized API responses.
 * Provides methods to generate responses with different HTTP statuses.
 */
public class ResponseUtil {

  /**
   * Creates a success response with HTTP status 200 (OK).
   *
   * @param <T>     the type of the response data
   * @param message the success message
   * @param data    the response data
   * @return an ApiResponse object with status 200
   */
  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(HttpStatus.OK, message, data);
  }

  /**
   * Creates a response for a successfully created resource with HTTP status 201 (Created).
   *
   * @param <T>     the type of the response data
   * @param message the success message
   * @param data    the response data
   * @return an ApiResponse object with status 201
   */
  public static <T> ApiResponse<T> created(String message, T data) {
    return new ApiResponse<>(HttpStatus.CREATED, message, data);
  }

  /**
   * Creates a response for a resource not found with HTTP status 404 (Not Found).
   *
   * @param <T>     the type of the response data
   * @param message the error message
   * @param data    the response data
   * @return an ApiResponse object with status 404
   */
  public static <T> ApiResponse<T> notFound(String message, T data) {
    return new ApiResponse<>(HttpStatus.NOT_FOUND, message, data);
  }

  /**
   * Creates an error response with HTTP status 400 (Bad Request).
   *
   * @param <T>     the type of the response data
   * @param message the error message
   * @param data    the response data
   * @return an ApiResponse object with status 400
   */
  public static <T> ApiResponse<T> error(String message, T data) {
    return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, data);
  }
}