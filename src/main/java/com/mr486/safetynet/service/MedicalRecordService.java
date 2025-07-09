package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing medical records.
 * Provides methods to retrieve, save, update, and delete medical records.
 */
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;

  /**
   * Retrieves a medical record by first name and last name.
   *
   * @param medicalRecordDto the DTO containing first name and last name
   * @return an Optional containing the MedicalRecord if found, otherwise empty
   */
  public Optional<MedicalRecord> getMedicalRecordByFirstNameAndLastName(MedicalRecordDto medicalRecordDto) {
    return medicalRecordRepository.findByFirstNameAndLastName(medicalRecordDto);
  }

  /**
   * Retrieves a medical record by first name and last name.
   *
   * @param medicalRecord the MedicalRecord object containing first name and last name
   */
  public void saveMedicalRecord(MedicalRecord medicalRecord) {
    MedicalRecordDto medicalRecordDto = new MedicalRecordDto(medicalRecord.getFirstName(), medicalRecord.getLastName());
    if (medicalRecordRepository.exists(medicalRecordDto)) {
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
    MedicalRecordDto medicalRecordDto = new MedicalRecordDto(medicalRecord.getFirstName(), medicalRecord.getLastName());
    if (!medicalRecordRepository.exists(medicalRecordDto)) {
      String message = "Medical record for first name [" + medicalRecord.getFirstName() + "] and last name [" + medicalRecord.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    medicalRecordRepository.update(medicalRecord);
  }

  /**
   * Deletes a medical record.
   *
   * @param medicalRecordDto the DTO containing first name and last name of the medical record to delete
   */
  public void deleteMedicalRecord(MedicalRecordDto medicalRecordDto) {
    if (!medicalRecordRepository.exists(medicalRecordDto)) {
      String message = "Medical record for first name [" + medicalRecordDto.getFirstName() + "] and last name [" + medicalRecordDto.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    medicalRecordRepository.delete(medicalRecordDto);
  }
}
