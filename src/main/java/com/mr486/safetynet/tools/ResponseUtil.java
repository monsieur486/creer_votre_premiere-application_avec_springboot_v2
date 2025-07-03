package com.mr486.safetynet.tools;

import org.springframework.http.HttpStatus;

public class ResponseUtil {
  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(HttpStatus.OK, message, data);
  }

  public static <T> ApiResponse<T> created(String message, T data) {
    return new ApiResponse<>(HttpStatus.CREATED, message, data);
  }

  public static <T> ApiResponse<T> notFound(String message, T data) {
    return new ApiResponse<>(HttpStatus.NOT_FOUND, message, data);
  }

  public static <T> ApiResponse<T> error(String message, T data) {
    return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, data);
  }
}
