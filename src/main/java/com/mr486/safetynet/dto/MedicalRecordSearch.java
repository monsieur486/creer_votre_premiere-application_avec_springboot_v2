package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.MedicalRecord;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for MedicalRecord.
 * This class is used to transfer medical record data between layers.
 * It contains the first name and last name of a person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordSearch {

  /**
   * The first name of the person.
   */
  @NotBlank(message = "firstname cannot be blank")
  private String firstName;

  /**
   * The last name of the person.
   */
  @NotBlank(message = "lastname cannot be blank")
  private String lastName;

  /**
   * Default constructor for MedicalRecordSearch.
   * Initializes an empty MedicalRecordSearch.
   *
   * @param medicalRecord the MedicalRecord object to initialize the DTO from.
   */
  public MedicalRecordSearch(MedicalRecord medicalRecord) {
    this.firstName = medicalRecord.getFirstName();
    this.lastName = medicalRecord.getLastName();
  }
}
