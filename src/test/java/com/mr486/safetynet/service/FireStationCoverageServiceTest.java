package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FireStationCoverage;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the FireStationCoverageService class.
 * Tests the functionality of retrieving coverage information for fire stations.
 */
class FireStationCoverageServiceTest {

  private FireStationService fireStationService;
  private PersonRepository personRepository;
  private MedicalRecordService medicalRecordService;
  private FireStationCoverageService fireStationCoverageService;

  @BeforeEach
  void setUp() {
    fireStationService = mock(FireStationService.class);
    personRepository = mock(PersonRepository.class);
    medicalRecordService = mock(MedicalRecordService.class);
    fireStationCoverageService = new FireStationCoverageService(fireStationService, personRepository, medicalRecordService);
  }

  /**
   * Tests the getCoverageByStationNumber method for a valid station number.
   * It checks if the method returns the correct coverage information including adult and child counts.
   */
  @Test
  void returnsCoverageForValidStationNumber() {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("123 Main St");

    when(fireStationService.getAllFireStationsByStationNumber(1))
            .thenReturn(List.of(new FireStation("123 Main St", 1)));
    when(personRepository.findPersonsByAddress("123 Main St"))
            .thenReturn(List.of(person));
    when(medicalRecordService.getMedicalRecordByFirstNameAndLastName(any()))
            .thenReturn(Optional.of(new MedicalRecord("John", "Doe", "01/01/2000")));
    when(medicalRecordService.isAdult(any()))
            .thenReturn(true);

    FireStationCoverage result = fireStationCoverageService.getCoverageByStationNumber(1);

    assertEquals(1, result.getAdultCount());
    assertEquals(0, result.getChildCount());
    assertEquals(1, result.getPersons().size());
  }

  /**
   * Tests the getCoverageByStationNumber method for a station number that does not exist.
   * It checks if the method returns an empty coverage with zero counts.
   */
  @Test
  void returnsEmptyCoverageForNonExistentStationNumber() {
    when(fireStationService.getAllFireStationsByStationNumber(99))
            .thenReturn(List.of());

    FireStationCoverage result = fireStationCoverageService.getCoverageByStationNumber(99);

    assertEquals(0, result.getAdultCount());
    assertEquals(0, result.getChildCount());
    assertTrue(result.getPersons().isEmpty());
  }

  /**
   * Tests the getCoverageByStationNumber method when no persons are found at the station's address.
   * It checks if the method returns an empty coverage with zero counts.
   */
  @Test
  void handlesMissingMedicalRecordGracefully() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Doe");
    person.setAddress("123 Main St");

    when(fireStationService.getAllFireStationsByStationNumber(1))
            .thenReturn(List.of(new FireStation("123 Main St", 1)));
    when(personRepository.findPersonsByAddress("123 Main St"))
            .thenReturn(List.of(person));
    when(medicalRecordService.getMedicalRecordByFirstNameAndLastName(any()))
            .thenReturn(Optional.empty());

    FireStationCoverage result = fireStationCoverageService.getCoverageByStationNumber(1);

    assertEquals(1, result.getAdultCount());
    assertEquals(0, result.getChildCount());
    assertEquals(1, result.getPersons().size());
  }

  /**
   * Tests the getCoverageByStationNumber method with multiple persons at the same address.
   * It checks if the method correctly counts adults and children.
   */
  @Test
  void correctlyCountsAdultsAndChildren() {
    when(fireStationService.getAllFireStationsByStationNumber(1))
            .thenReturn(List.of(new FireStation("123 Main St", 1)));
    when(personRepository.findPersonsByAddress("123 Main St"))
            .thenReturn(List.of(
                    new Person("John", "Doe", "123 Main St", "city", "zip", "123-456-7891", "email@test.fr"),
                    new Person("Jane", "Doe", "123 Main St", "city", "zip", "123-456-7891", "email@test.fr")
            ));
    when(medicalRecordService.getMedicalRecordByFirstNameAndLastName(any()))
            .thenReturn(Optional.of(new MedicalRecord("John", "Doe", "01/01/2000")))
            .thenReturn(Optional.of(new MedicalRecord("Jane", "Doe", "01/01/2015")));
    when(medicalRecordService.isAdult(any()))
            .thenReturn(true)
            .thenReturn(false);

    FireStationCoverage result = fireStationCoverageService.getCoverageByStationNumber(1);

    assertEquals(1, result.getAdultCount());
    assertEquals(1, result.getChildCount());
    assertEquals(2, result.getPersons().size());
  }
}