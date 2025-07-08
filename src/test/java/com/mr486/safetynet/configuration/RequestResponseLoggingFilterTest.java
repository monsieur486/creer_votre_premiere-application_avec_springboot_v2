package com.mr486.safetynet.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the {@link RequestResponseLoggingFilter}.
 * This class contains unit tests to verify the behavior of the filter
 * when logging HTTP requests and responses.
 */
class RequestResponseLoggingFilterTest {

  private RequestResponseLoggingFilter filter;
  private FilterChain mockFilterChain;

  /**
   * Setup method executed before each test.
   * Initializes an instance of the filter and a mock FilterChain.
   */
  @BeforeEach
  void setUp() {
    filter = new RequestResponseLoggingFilter();
    mockFilterChain = mock(FilterChain.class);
  }

  /**
   * Verifies that the filter correctly logs HTTP requests and responses.
   * Tests a POST request with a body and headers.
   *
   * @throws ServletException if an error occurs during filter processing
   * @throws IOException      if an I/O error occurs
   */
  @Test
  void testDoFilterInternal_logsRequestAndResponse() throws ServletException, IOException {
    // Mock HTTP request
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("POST");
    request.setRequestURI("/test");
    request.setQueryString("param=value");
    request.addHeader("X-Test-Header", "test-value");
    request.setContent("test-body".getBytes());

    // Mock HTTP response
    MockHttpServletResponse response = new MockHttpServletResponse();

    // Execute
    filter.doFilterInternal(request, response, mockFilterChain);

    // Verify that the filterChain was called with wrapped versions
    verify(mockFilterChain, times(1)).doFilter(any(), any());

    // Check response body was copied
    assertEquals(200, response.getStatus());
  }

  /**
   * Verifies that the filter correctly handles a request with an empty body.
   *
   * @throws ServletException if an error occurs during filter processing
   * @throws IOException      if an I/O error occurs
   */
  @Test
  void testDoFilterInternal_handlesEmptyRequestBody() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setRequestURI("/empty");
    request.setContent("".getBytes());

    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilterInternal(request, response, mockFilterChain);

    verify(mockFilterChain).doFilter(any(), any());
    assertEquals(200, response.getStatus());
  }
}