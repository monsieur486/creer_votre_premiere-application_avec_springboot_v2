package com.mr486.safetynet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for Fire Station Coverage.
 * Contains information about persons covered by a fire station,
 * including counts of adults and children.
 */
@Data
@AllArgsConstructor
public class FireStationCoverageDto {
  private List<PersonInfo> persons;
  private long adultCount;
  private long childCount;

  /**
   * Constructor to initialize FireStationCoverageDto with a list of persons.
   * Automatically calculates the adult and child counts based on the provided persons.
   *
   */
  @Data
  @AllArgsConstructor
  public static class PersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }
}
