package com.mr486.safetynet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Person Information.
 * Contains personal details such as first name, last name, address, and phone number.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfo {
  private String firstName;
  private String lastName;
  private String address;
  private String phone;
}
