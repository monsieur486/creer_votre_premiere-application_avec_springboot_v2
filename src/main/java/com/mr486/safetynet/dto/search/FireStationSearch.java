package com.mr486.safetynet.dto.search;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a fire station.
 * This DTO contains the address of a fire station and is used for data validation and transfer
 * between layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationSearch {
  /**
   * The address of the fire station.
   * Must not be blank.
   */
  @NotBlank(message = "Address cannot be blank")
  private String address;
}