package com.mr486.safetynet.domain.domain_medicalrecords.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Represents a medical record containing personal and medical information.
 * This class is a Spring component and uses Lombok annotations to reduce boilerplate code.
 */
@Component
@Data
@NoArgsConstructor
public class MedicalRecord {

  /**
   * The first name of the person.
   */
  private String firstName;

  /**
   * The last name of the person.
   */
  private String lastName;

  /**
   * The birthdate of the person in the format "MM/dd/yyyy".
   */
  private String birthdate;

  /**
   * A list of medications the person is taking.
   */
  private ArrayList<String> medications;

  /**
   * A list of allergies the person has.
   */
  private ArrayList<String> allergies;
}