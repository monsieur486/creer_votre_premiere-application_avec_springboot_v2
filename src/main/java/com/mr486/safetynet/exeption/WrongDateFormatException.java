package com.mr486.safetynet.exeption;

/**
 * Exception thrown when a date format is incorrect.
 * This custom exception is used to indicate errors related to invalid date formats.
 */
public class WrongDateFormatException extends RuntimeException {

  /**
   * Constructs a new WrongDateFormatException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public WrongDateFormatException(String message) { super(message); }
}
