package com.mr486.safetynet.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * Application configuration class.
 * Contains constants used throughout the application.
 */
@Configuration
public class AppConfiguration {
    // Constants for API response messages

    // Minimum age for a person to be considered an adult
    public static final int AGE_ADULT = 18;

    // Default date format used in the application
    public static final String DATE_FORMAT = "MM/dd/yyyy";
}
