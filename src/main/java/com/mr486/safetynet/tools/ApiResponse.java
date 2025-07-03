package com.mr486.safetynet.tools;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Generic API response class for standardizing HTTP responses.
 *
 * @param <T> the type of the data included in the response
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

  /**
   * The HTTP status of the response.
   */
  private HttpStatus status;

  /**
   * A message providing additional information about the response.
   */
  private String message;

  /**
   * The data payload of the response.
   */
  private T data;

  /**
   * Constructs an ApiResponse with the specified status, message, and data.
   *
   * @param status  the HTTP status of the response
   * @param message the message providing additional information
   * @param data    the data payload of the response
   */
  public ApiResponse(HttpStatus status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }
}