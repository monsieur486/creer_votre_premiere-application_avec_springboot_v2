package com.mr486.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a person with personal information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  /**
   * The person's first name.
   */
  private String firstName;

  /**
   * The person's last name.
   */
  private String lastName;

  /**
   * The person's residential address.
   */
  private String address;

  /**
   * The city where the person resides.
   */
  private String city;

  /**
   * The postal code of the city.
   */
  private String zip;

  /**
   * The person's phone number.
   */
  private String phone;

  /**
   * The person's email address.
   */
  private String email;
}