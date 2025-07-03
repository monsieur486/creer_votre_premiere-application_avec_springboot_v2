package com.mr486.safetynet.domain.domain_firestation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Represents a fire station entity with an address and a station number.
 * This class is a Spring component and uses Lombok annotations for boilerplate code reduction.
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStation {

  /**
   * The address of the fire station.
   * This field cannot be blank.
   */
  @NotBlank(message = "Address cannot be blank")
  private String address;

  /**
   * The station number of the fire station.
   * This field cannot be null.
   */
  @NotNull(message = "Station number cannot be null")
  private Integer station;
}