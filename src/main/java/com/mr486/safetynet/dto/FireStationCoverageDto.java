package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Fire Station Coverage.
 * Contains information about persons covered by a fire station,
 * including counts of adults and children.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationCoverageDto {
  private List<PersonInfoDto> persons;
  private long adultCount;
  private long childCount;

  /**
   * Adds a person to the list of persons covered by the fire station.
   *
   * @param person the person to be added
   */
  public void addPerson(PersonInfoDto person) {
    if (persons == null) {
      persons = new ArrayList<>();
    }
    persons.add(person);
  }
}
