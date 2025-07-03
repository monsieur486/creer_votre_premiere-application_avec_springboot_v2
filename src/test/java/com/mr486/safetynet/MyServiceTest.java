package com.mr486.safetynet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the `MyService` class.
 */
public class MyServiceTest {

  /**
   * Tests a simple addition operation to verify basic arithmetic.
   */
  @Test
  void testAddition() {
    assertEquals(2, 1 + 1); // Asserts that 1 + 1 equals 2
  }

  /**
   * Tests the behavior of a mocked `MyService` instance using Mockito.
   * Verifies that the mocked method `doSomething` returns the expected value.
   */
  @Test
  void testMockito() {
    MyService service = mock(MyService.class); // Creates a mock instance of MyService
    when(service.doSomething()).thenReturn("ok"); // Configures the mock to return "ok" for doSomething()
    assertEquals("ok", service.doSomething()); // Asserts that the mocked method returns "ok"
  }

  /**
   * A simple service class with a method `doSomething`.
   * This class is used for testing purposes.
   */
  static class MyService {
    /**
     * A method that returns a string value.
     * @return the string "real"
     */
    String doSomething() {
      return "real";
    }
  }
}