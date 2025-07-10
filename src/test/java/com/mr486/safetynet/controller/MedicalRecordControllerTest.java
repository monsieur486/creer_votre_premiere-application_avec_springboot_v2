package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the MedicalRecordController class.
 * This class tests the addMedicalRecord, updateMedicalRecord, and deleteMedicalRecord methods of the MedicalRecordController.
 */
class MedicalRecordControllerTest {

  @Mock
  private MedicalRecordService medicalRecordService;

  @InjectMocks
  private MedicalRecordController medicalRecordController;

  /**
   * Initializes mocks and sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests the addMedicalRecord method of the MedicalRecordController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void addMedicalRecord_shouldAddMedicalRecordSuccessfully() throws Exception {
    // Given a valid MedicalRecord object
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");

    // When the addMedicalRecord method is called
    ResponseEntity<String> responseEntity = medicalRecordController.addMedicalRecord(medicalRecord);

    // Then verify that the service method was called
    verify(medicalRecordService, times(1)).saveMedicalRecord(medicalRecord);

    // And check the response
    assertEquals(201, responseEntity.getStatusCodeValue());
    assertEquals("Medical record added successfully", responseEntity.getBody());
  }

  /**
   * Tests the updateMedicalRecord method of the MedicalRecordController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void updateMedicalRecord_shouldUpdateMedicalRecordSuccessfully() throws Exception {
    // Given a valid MedicalRecord object
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");

    // When the updateMedicalRecord method is called
    ResponseEntity<String> responseEntity = medicalRecordController.updateMedicalRecord(medicalRecord);

    // Then verify that the service method was called
    verify(medicalRecordService, times(1)).updateMedicalRecord(medicalRecord);

    // And check the response
    assertEquals(201, responseEntity.getStatusCodeValue());
    assertEquals("Medical record updated successfully", responseEntity.getBody());
  }

  /**
   * Tests the deleteMedicalRecord method of the MedicalRecordController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void deleteMedicalRecord_shouldDeleteMedicalRecordSuccessfully() throws Exception {
    // Given a valid MedicalRecordSearch object
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch();
    medicalRecordSearch.setFirstName("John");
    medicalRecordSearch.setLastName("Doe");

    // When the deleteMedicalRecord method is called
    ResponseEntity<String> responseEntity = medicalRecordController.deleteMedicalRecord(medicalRecordSearch);

    // Then verify that the service method was called
    verify(medicalRecordService, times(1)).deleteMedicalRecord(medicalRecordSearch);

    // And check the response
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertEquals("Medical record deleted successfully", responseEntity.getBody());
  }

}