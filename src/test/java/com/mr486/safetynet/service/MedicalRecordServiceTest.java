package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto(medicalRecord);

    when(medicalRecordRepository.findByFirstNameAndLastName(medicalRecordDto1))
        .thenReturn(Optional.of(medicalRecord));

    Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecordDto1);
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

    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordDto1)).thenReturn(false);

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

    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordDto1)).thenReturn(true);

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

    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordDto1)).thenReturn(true);

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

    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto(medicalRecord);

    when(medicalRecordRepository.exists(medicalRecordDto1)).thenReturn(false);

    assertThrows(EntityAlreadyExistsException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord));
    verify(medicalRecordRepository, never()).save(any());
  }

  /**
   * Test for deleting a medical record that exists
   */
  @Test
  void testDeleteMedicalRecord_success() {
    MedicalRecordDto medicalRecordDto1 = new MedicalRecordDto("John", "Doe");
    when(medicalRecordRepository.exists(medicalRecordDto1)).thenReturn(true);

    medicalRecordService.deleteMedicalRecord(medicalRecordDto1);

    verify(medicalRecordRepository).delete(medicalRecordDto1);
  }

  /**
   * Test for deleting a medical record that does not exist
   */
  @Test
  void testDeleteMedicalRecord_notFound() {
    MedicalRecordDto medicalRecordDto = new MedicalRecordDto("John", "Doe");
    when(medicalRecordRepository.exists(medicalRecordDto)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> medicalRecordService.deleteMedicalRecord(medicalRecordDto));
    verify(medicalRecordRepository, never()).delete(any());


  }

}