package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.MedicalRecordSearch;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for MedicalRecordService
 */
class MedicalRecordServiceTest {

  private MedicalRecordRepository medicalRecordRepository;
  private MedicalRecordService medicalRecordService;

  @BeforeEach
  void setUp() {
    medicalRecordRepository = mock(MedicalRecordRepository.class);
    medicalRecordService = new MedicalRecordService(medicalRecordRepository);
  }

  /**
   * Test for getting a medical record by first name and last name
   */
  @Test
  void testGetMedicalRecordByFirstNameAndLastName_found() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");

    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch(medicalRecord);

    when(medicalRecordRepository.findByFirstNameAndLastName(medicalRecordSearch1))
            .thenReturn(Optional.of(medicalRecord));

    Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecordSearch1);
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  /**
   * Test for getting a medical record by first name and last name when not found
   */
  @Test
  void saveMedicalRecord_found() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Jane");
    medicalRecord.setLastName("Smith");

    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordSearch1)).thenReturn(false);

    medicalRecordService.saveMedicalRecord(medicalRecord);

    // Verify that the save method was called
    verify(medicalRecordRepository).save(medicalRecord);
  }

  /**
   * Test for saving a medical record that already exists
   */
  @Test
  void saveMedicalRecord_alreadyExists() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Jane");
    medicalRecord.setLastName("Smith");

    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordSearch1)).thenReturn(true);

    assertThrows(EntityAlreadyExistsException.class, () -> medicalRecordService.saveMedicalRecord(medicalRecord));
  }

  /**
   * Test for updating a medical record that exists
   */
  @Test
  void testUpdateMedicalRecord_success() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Jake");
    medicalRecord.setLastName("Johnson");

    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordSearch1)).thenReturn(true);

    medicalRecordService.updateMedicalRecord(medicalRecord);

    // Verify that the save method was called
    verify(medicalRecordRepository).update(medicalRecord);
  }

  /**
   * Test for updating a medical record that does not exist
   */
  @Test
  void testUpdateMedicalRecord_notFound() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Jake");
    medicalRecord.setLastName("Johnson");

    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordSearch1)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord));
    verify(medicalRecordRepository, never()).save(any());
  }

  /**
   * Test for deleting a medical record that exists
   */
  @Test
  void testDeleteMedicalRecord_success() {
    MedicalRecordSearch medicalRecordSearch1 = new MedicalRecordSearch("John", "Doe");
    when(medicalRecordRepository.exists(medicalRecordSearch1)).thenReturn(true);

    medicalRecordService.deleteMedicalRecord(medicalRecordSearch1);

    verify(medicalRecordRepository).delete(medicalRecordSearch1);
  }

  /**
   * Test for deleting a medical record that does not exist
   */
  @Test
  void testDeleteMedicalRecord_notFound() {
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch("John", "Doe");
    when(medicalRecordRepository.exists(medicalRecordSearch)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> medicalRecordService.deleteMedicalRecord(medicalRecordSearch));
    verify(medicalRecordRepository, never()).delete(any());
  }

  /**
   * Test for checking if a medical record belongs to an adult
   */
  @Test
  void testIsAdult() throws NoSuchFieldException, IllegalAccessException {

    Field field = MedicalRecordService.class.getDeclaredField("adultAge");
    field.setAccessible(true);
    field.set(medicalRecordService, 18);

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("12/21/2000");
    boolean isAdult = medicalRecordService.isAdult(medicalRecord);
    assertTrue(isAdult, "Expected the person to be an adult based on the birthdate.");

    medicalRecord.setBirthdate("12/21/2020");
    isAdult = medicalRecordService.isAdult(medicalRecord);
    assertFalse(isAdult, "Expected the person to not be an adult based on the birthdate.");

  }

  /**
   * Test for checking if a medical record does not belong to an adult
   */
  @Test
  void testIsAdult_withBadBirthdate() throws NoSuchFieldException, IllegalAccessException {
    Field field = MedicalRecordService.class.getDeclaredField("adultAge");
    field.setAccessible(true);
    field.set(medicalRecordService, 18);

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("invalid-date");

    assertThrows(IllegalArgumentException.class, () -> medicalRecordService.isAdult(medicalRecord));
  }

  /**
   * Test for calculating age based on birthdate
   */
  @Test
  void testCalculateAge() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");

    int age = medicalRecordService.calculateAge(medicalRecord.getBirthdate());
    LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    int expectedAge = LocalDate.now().getYear() - birthDate.getYear();

    assertEquals(expectedAge, age, "Expected age to match the calculated age based on the birthdate.");
  }

  /**
   * Test for calculating age with an invalid birthdate format
   */
  @Test
  void testCalculateAge_withInvalidBirthdate() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("invalid-date");

    assertThrows(IllegalArgumentException.class, () -> medicalRecordService.calculateAge(medicalRecord.getBirthdate()));
  }


}