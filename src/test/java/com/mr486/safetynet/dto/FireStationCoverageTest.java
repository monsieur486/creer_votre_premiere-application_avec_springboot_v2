package com.mr486.safetynet.dto;

import com.mr486.safetynet.dto.response.FireStationCoverage;
import com.mr486.safetynet.dto.response.PersonInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the FireStationCoverage class.
 * Tests the functionality of adding persons to the coverage list.
 */
class FireStationCoverageTest {

  /**
   * Tests adding a person to an empty FireStationCoverage.
   * Verifies that the person is added correctly.
   */
  @Test
  void addsPersonToEmptyList() {
    FireStationCoverage dto = new FireStationCoverage();
    PersonInfo person = new PersonInfo("John", "Doe", "123 Main St", "123-456-7890");

    dto.addPerson(person);

    assertEquals(1, dto.getPersons().size());
    assertEquals(person, dto.getPersons().get(0));
  }
}