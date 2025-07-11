package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.AppConfiguration;
import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Service class for managing medical records.
 * Provides methods to retrieve, save, update, and delete medical records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;

  /**
   * Retrieves a medical record by first name and last name.
   *
   * @param medicalRecordSearch the DTO containing first name and last name
   * @return an Optional containing the MedicalRecord if found, otherwise empty
   */
  public Optional<MedicalRecord> getMedicalRecordByFirstNameAndLastName(MedicalRecordSearch medicalRecordSearch) {
    return medicalRecordRepository.findByFirstNameAndLastName(medicalRecordSearch);
  }

  /**
   * Retrieves a medical record by first name and last name.
   *
   * @param medicalRecord the MedicalRecord object containing first name and last name
   */
  public void saveMedicalRecord(MedicalRecord medicalRecord) {
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch(medicalRecord.getFirstName(), medicalRecord.getLastName());
    if (medicalRecordRepository.exists(medicalRecordSearch)) {
      String message = "Medical record for first name [" + medicalRecord.getFirstName() + "] and last name [" + medicalRecord.getLastName() + "] already exists!";
      throw new EntityAlreadyExistsException(message);
    }
    medicalRecordRepository.save(medicalRecord);
  }

  /**
   * Updates an existing medical record.
   *
   * @param medicalRecord the MedicalRecord object containing updated information
   */
  public void updateMedicalRecord(MedicalRecord medicalRecord) {
    MedicalRecordSearch medicalRecordSearch = new MedicalRecordSearch(medicalRecord.getFirstName(), medicalRecord.getLastName());
    if (!medicalRecordRepository.exists(medicalRecordSearch)) {
      String message = "Medical record for first name [" + medicalRecord.getFirstName() + "] and last name [" + medicalRecord.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    medicalRecordRepository.update(medicalRecord);
  }

  /**
   * Deletes a medical record.
   *
   * @param medicalRecordSearch the DTO containing first name and last name of the medical record to delete
   */
  public void deleteMedicalRecord(MedicalRecordSearch medicalRecordSearch) {
    if (!medicalRecordRepository.exists(medicalRecordSearch)) {
      String message = "Medical record for first name [" + medicalRecordSearch.getFirstName() + "] and last name [" + medicalRecordSearch.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    medicalRecordRepository.delete(medicalRecordSearch);
  }

  /**
   * Checks if a medical record belongs to an adult based on the birthdate.
   *
   * @param medicalRecord the MedicalRecord object to check
   * @return true if the person is an adult, false otherwise
   */
  public Boolean isAdult(MedicalRecord medicalRecord) {
    try {
      int age = calculateAge(medicalRecord.getBirthdate());
      return age >= AppConfiguration.AGE_ADULT;
    } catch (IllegalArgumentException e) {
      log.error("Invalid birthdate format for medical record: {}", medicalRecord, e);
      return false; // Consider as not an adult if the birthdate is invalid
    }
  }

  /**
   * Calculates the age based on the provided birthdate.
   *
   * @param birthdate the birthdate in AppConfiguration.DATE_FORMAT
   * @return the age in years
   */
  public int calculateAge(String birthdate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConfiguration.DATE_FORMAT);
    try {
      LocalDate now = LocalDate.now();
      return Period.between(LocalDate.parse(birthdate, formatter), now).getYears();
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid birthdate format. Expected format is: "+ AppConfiguration.DATE_FORMAT, e);
    }
  }

}
