package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBinding;
import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MedicalRecordRepository that uses JSON data storage.
 * This repository provides methods to save, update, find, delete, and check the existence of medical records.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordRepositoryImplJson implements MedicalRecordRepository {

  /**
   * The JSON data reader used to load fire station data.
   */
  private final JsonDataReader jsonDataReader;

  private List<MedicalRecord> medicalRecords = Collections.emptyList();

  /**
   * Initializes the repository by loading medical records data from a JSON file.
   * This method is called automatically after the bean is constructed.
   */
  @PostConstruct
  void init() {
    log.warn("MedicalRecords data loading from json file");
    try {
      DataBinding dataBinding = jsonDataReader.loadData();
      medicalRecords = new java.util.ArrayList<>(dataBinding.getMedicalrecords());
      log.warn("✅ \"MedicalRecords data loaded successfully, count: {}", medicalRecords.size());
    } catch (Exception e) {
      log.error("❌ {}", e.getMessage());
    }
  }

  /**
   * Saves a medical record to the repository.
   *
   * @param medicalRecord the medical record to save
   */
  @Override
  public void save(MedicalRecord medicalRecord) {
    medicalRecords.add(medicalRecord);
  }

  /**
   * Updates an existing medical record in the repository.
   *
   * @param medicalRecord the medical record to update
   */
  @Override
  public void update(MedicalRecord medicalRecord) {
    medicalRecords.replaceAll(existingMedicalRecord ->
            isMedicRecordDtoEqualMedicalRecord(
                    existingMedicalRecord,
                    new MedicalRecordSearch(
                            medicalRecord.getFirstName(),
                            medicalRecord.getLastName()))
                    ? medicalRecord : existingMedicalRecord
    );
  }

  /**
   * Finds a medical record by first name and last name.
   *
   * @param medicalRecordSearch the DTO containing first name and last name
   * @return an Optional containing the found medical record, or empty if not found
   */
  @Override
  public Optional<MedicalRecord> findByFirstNameAndLastName(MedicalRecordSearch medicalRecordSearch) {
    return medicalRecords.stream()
            .filter(medicalRecord -> isMedicRecordDtoEqualMedicalRecord(medicalRecord, medicalRecordSearch))
            .findFirst();
  }

  /**
   * Deletes a medical record from the repository.
   *
   * @param medicalRecord the DTO containing first name and last name of the medical record to delete
   */
  @Override
  public void delete(MedicalRecordSearch medicalRecord) {
    medicalRecords.removeIf(existingMedicalRecord ->
            isMedicRecordDtoEqualMedicalRecord(existingMedicalRecord, medicalRecord));
  }

  /**
   * Checks if a medical record exists in the repository.
   *
   * @param medicalRecordSearch the DTO containing first name and last name
   * @return true if the medical record exists, false otherwise
   */
  @Override
  public boolean exists(MedicalRecordSearch medicalRecordSearch) {
    return medicalRecords.stream()
            .anyMatch(medicalRecord -> isMedicRecordDtoEqualMedicalRecord(medicalRecord, medicalRecordSearch));
  }

  /**
   * Checks if a medical record DTO is equal to a medical record.
   *
   * @param medicalRecord       the medical record to compare
   * @param medicalRecordSearch the DTO to compare
   * @return true if they are equal, false otherwise
   */
  private boolean isMedicRecordDtoEqualMedicalRecord(MedicalRecord medicalRecord, MedicalRecordSearch medicalRecordSearch) {
    return medicalRecord.getFirstName().equals(medicalRecordSearch.getFirstName())
            && medicalRecord.getLastName().equals(medicalRecordSearch.getLastName());
  }
}
