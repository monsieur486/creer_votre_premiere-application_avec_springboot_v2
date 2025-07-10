package com.mr486.safetynet.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FireStationCoverageDto class.
 * Tests the functionality of adding persons to the coverage list.
 */
class FireStationCoverageDtoTest {

  /**
   * Tests adding a person to an empty FireStationCoverageDto.
   * Verifies that the person is added correctly.
   */
  @Test
  void addsPersonToEmptyList() {
    FireStationCoverageDto dto = new FireStationCoverageDto();
    PersonInfoDto person = new PersonInfoDto("John", "Doe", "123 Main St", "123-456-7890");

    dto.addPerson(person);

    assertEquals(1, dto.getPersons().size());
    assertEquals(person, dto.getPersons().get(0));
  }

  /**
   * Tests adding a person to a FireStationCoverageDto that already has persons.
   * Verifies that the new person is added to the existing list.
   */
  @Test
  void addNullPerson_shuldAddNewArrayList() {
    FireStationCoverageDto dto = new FireStationCoverageDto();
    PersonInfoDto person = null;

    dto.addPerson(person);

    assertNotNull(dto.getPersons());
    assertEquals(1, dto.getPersons().size());
    assertNull(dto.getPersons().get(0));
  }

}