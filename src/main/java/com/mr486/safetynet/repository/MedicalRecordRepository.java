package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;

import java.util.Optional;

/**
 * Repository interface for managing medical records.
 * This interface defines methods for saving, updating, finding, deleting, and checking the existence of medical records.
 */
public interface MedicalRecordRepository {

  /**
   * Saves a medical record to the repository.
   *
   * @param medicalRecord the medical record to save
   */
  void save(MedicalRecord medicalRecord);

  /**
   * Updates an existing medical record in the repository.
   *
   * @param medicalRecord the medical record to update
   */
  void update(MedicalRecord medicalRecord);

  /**
   * Finds a medical record by first and last name.
   *
   * @param medicalRecordSearch the DTO containing the first and last name of the person
   * @return an Optional containing the found medical record, or empty if not found
   */
  Optional<MedicalRecord> findByFirstNameAndLastName(MedicalRecordSearch medicalRecordSearch);

  /**
   * Deletes a medical record from the repository.
   *
   * @param medicalRecordSearch the DTO containing the first and last name of the person whose record is to be deleted
   */
  void delete(MedicalRecordSearch medicalRecordSearch);

  /**
   * Checks if a medical record exists for a given person.
   *
   * @param medicalRecordSearch the DTO containing the first and last name of the person
   * @return true if the medical record exists, false otherwise
   */
  boolean exists(MedicalRecordSearch medicalRecordSearch);
}
