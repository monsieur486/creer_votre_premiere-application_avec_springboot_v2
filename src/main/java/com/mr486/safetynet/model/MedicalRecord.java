package com.mr486.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * Represents a medical record containing personal and medical information.
 */
@Data
@AllArgsConstructor
public class MedicalRecord {

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
   * The birthdate of the person in the format "MM/dd/yyyy".
   */
  @NotBlank(message = "birthdate cannot be blank")
  private String birthdate;

  /**
   * A list of medications the person is taking.
   */
  private ArrayList<String> medications;

  /**
   * A list of allergies the person has.
   */
  private ArrayList<String> allergies;

  /**
   * Default constructor for MedicalRecord.
   * Initializes an empty MedicalRecord with empty lists for medications and allergies.
   */
  public MedicalRecord() {
    this.medications = new ArrayList<>();
    this.allergies = new ArrayList<>();
  }

  /**
   * Constructor for MedicalRecord with first name, last name, and birthdate.
   * Initializes empty lists for medications and allergies.
   *
   * @param firstName The first name of the person.
   * @param lastName  The last name of the person.
   * @param birthdate The birthdate of the person in the format "MM/dd/yyyy".
   */
  public MedicalRecord(String firstName, String lastName, String birthdate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.medications = new ArrayList<>();
    this.allergies = new ArrayList<>();
  }

  /**
   * Calculates the age of the person based on their birthdate.
   *
   * @return The age of the person.
   * @throws IllegalArgumentException if the birthdate format is invalid.
   */
  public int getAge() {
    String[] parts = birthdate.split("/");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid birthdate format. Expected format: MM/dd/yyyy");
    }

    int birthYear = Integer.parseInt(parts[2]);
    int currentYear = java.time.Year.now().getValue();

    return currentYear - birthYear;
  }
}