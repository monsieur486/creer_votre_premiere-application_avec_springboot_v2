package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBinding;
import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.tools.JsonDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MedicalRecordRepositoryImplJsonTest {

  @Mock
  private JsonDataReader mockJsonDataReader; // Mocked JsonDataReader to simulate data loading.

  @InjectMocks
  private MedicalRecordRepositoryImplJson medicalRecordRepository; // Repository under test.

  /**
   * Initializes mocks and sets up the repository before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests that the repository initializes correctly and loads medical records from JSON.
   */
  @Test
  void init_shouldLoadMedicalRecordsFromJson() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch(medicalRecord);
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(medicalRecordSearch);
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  /**
   * Tests that the repository initializes with an empty list when no medical records are present in JSON.
   */
  @Test
  void init_shouldHandleExceptionAndKeepFireStationsEmpty() {
    when(mockJsonDataReader.loadData()).thenThrow(new RuntimeException("Error loading data"));

    medicalRecordRepository.init();

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Doe"));
    assertTrue(result.isEmpty());

  }

  /**
   * Tests that the repository can save a medical record successfully.
   */
  @Test
  void savesMedicalRecordSuccessfully() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());

  }

  /**
   * Updates an existing medical record and verifies the change.
   */
  @Test
  void update_shouldModifyExistingMedicalRecord() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    MedicalRecord updatedMedicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/2001"
    );
    medicalRecordRepository.update(updatedMedicalRecord);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("01/01/2001", result.get().getBirthdate());
  }

  /**
   * Tests that the repository can find a medical record by first and last name.
   */
  @Test
  void findByFirstNameAndLastName() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  /**
   * Tests that the repository can delete a medical record.
   */
  @Test
  void delete_shouldRemoveMedicalRecord() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch(medicalRecord);
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    medicalRecordRepository.delete(medicalRecordSearch);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(medicalRecordSearch);
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that the repository handles non-existent medical records gracefully.
   */
  @Test
  void handlesNonExistentMedicalRecordGracefully() {
    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("NonExistent", "Person"));
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that the repository can check if a medical record exists.
   */
  @Test
  void checkExistingMedicalRecord() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    boolean exists = medicalRecordRepository.exists(new MedicalRecordSearch("John", "Doe"));
    assertTrue(exists);

    boolean notExists = medicalRecordRepository.exists(new MedicalRecordSearch("Jane", "Doe"));
    assertFalse(notExists);
  }

  /**
   * Tests that the repository returns an empty optional if the first name does not match.
   */
  @Test
  void findByFirstNameAndLastName_shouldReturnEmptyIfLastNameNotMatch() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Smith"));
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that the repository returns an empty optional if the first name does not match.
   */
  @Test
  void update_shouldNotModifyIfMedicalRecordDoesNotExist() {
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1990"
    );
    DataBinding dataBinding = new DataBinding();
    dataBinding.setMedicalrecords(List.of(medicalRecord));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    medicalRecordRepository.init();
    medicalRecordRepository.save(medicalRecord);

    MedicalRecord nonExistentRecord = new MedicalRecord(
            "Jane",
            "Smith",
            "02/02/1992"
    );
    medicalRecordRepository.update(nonExistentRecord);

    Optional<MedicalRecord> result = medicalRecordRepository.findByFirstNameAndLastName(new MedicalRecordSearch("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("01/01/1990", result.get().getBirthdate());
  }

}