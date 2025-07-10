package com.mr486.safetynet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Fire Station Coverage.
 * Contains information about persons covered by a fire station,
 * including counts of adults and children.
 */
@Data
@AllArgsConstructor
public class FireStationCoverage {
  private List<PersonInfo> persons;
  private long adultCount;
  private long childCount;

  /**
   * Default constructor initializes an empty list of persons.
   */
  public FireStationCoverage() {
    this.persons = new ArrayList<>();
  }

  /**
   * Adds a person to the list of persons covered by the fire station.
   *
   * @param person the person to be added
   */
  public void addPerson(PersonInfo person) {
    persons.add(person);
  }
}
