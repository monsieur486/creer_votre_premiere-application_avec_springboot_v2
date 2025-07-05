package com.mr486.safetynet.tools.json_file;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a data binding component that holds lists of persons, fire stations, and medical records.
 * This class is a Spring component and uses Lombok annotations to reduce boilerplate code.
 */
@Data
public class DataBinding {

  /**
   * A list of persons.
   */
  List<Person> persons;

  /**
   * A list of fire stations.
   */
  List<FireStation> firestations;

  /**
   * A list of medical records.
   */
  List<MedicalRecord> medicalrecords;

  public DataBinding() {
    this.persons = new ArrayList<>();
    this.firestations = new ArrayList<>();
    this.medicalrecords = new ArrayList<>();
  }
}