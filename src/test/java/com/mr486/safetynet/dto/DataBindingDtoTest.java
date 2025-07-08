package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DataBindingDto} class.
 * This test class verifies the correct behavior of the DataBindingDto,
 * including initialization and manipulation of persons, firestations, and medical records lists.
 */
class DataBindingDtoTest {

  /**
   * Instance of DataBindingDto used for testing.
   */
  private DataBindingDto dataBindingDto;

  /**
   * Initializes a new DataBindingDto before each test.
   */
  @BeforeEach
  void setUp() {
    dataBindingDto = new DataBindingDto();
  }

  /**
   * Verifies that the constructor initializes empty lists for persons, firestations, and medical records.
   */
  @Test
  void constructor_shouldInitializeEmptyLists() {
    assertNotNull(dataBindingDto.getPersons());
    assertNotNull(dataBindingDto.getFirestations());
    assertNotNull(dataBindingDto.getMedicalrecords());
    assertTrue(dataBindingDto.getPersons().isEmpty());
    assertTrue(dataBindingDto.getFirestations().isEmpty());
    assertTrue(dataBindingDto.getMedicalrecords().isEmpty());
  }

  /**
   * Verifies that setPersons updates the persons list.
   */
  @Test
  void setPersons_shouldUpdatePersonsList() {
    List<Person> persons = List.of(new Person(
            "John",
            "Doe",
            "123 Street",
            "City",
            "12345",
            "123-456-7890",
            "john.doe@example.com"));
    dataBindingDto.setPersons(persons);
    assertEquals(persons, dataBindingDto.getPersons());
  }

  /**
   * Verifies that setFirestations updates the firestations list.
   */
  @Test
  void setFirestations_shouldUpdateFirestationsList() {
    List<FireStation> firestations = List.of(new FireStation("123 Street", 1));
    dataBindingDto.setFirestations(firestations);
    assertEquals(firestations, dataBindingDto.getFirestations());
  }

  /**
   * Verifies that setMedicalrecords updates the medical records list.
   */
  @Test
  void setMedicalrecords_shouldUpdateMedicalRecordsList() {
    ArrayList<String> medications = new ArrayList<>();
    medications.add("med1");
    medications.add("med2");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("allergy1");
    allergies.add("allergy2");
    MedicalRecord medicalRecord = new MedicalRecord(
            "Jane",
            "Doe",
            "02/02/2000",
            medications,
            allergies);
    List<MedicalRecord> medicalRecords = List.of(medicalRecord);
    dataBindingDto.setMedicalrecords(medicalRecords);
    assertEquals(medicalRecords, dataBindingDto.getMedicalrecords());
  }

  /**
   * Verifies that a person can be added to the persons list.
   */
  @Test
  void addPerson_shouldAddPersonToList() {
    Person person = new Person(
            "Jane",
            "Doe",
            "456 Avenue",
            "City",
            "67890",
            "987-654-3210",
            "jane.doe@example.com");
    dataBindingDto.getPersons().add(person);
    assertTrue(dataBindingDto.getPersons().contains(person));
  }

  /**
   * Verifies that a fire station can be added to the firestations list.
   */
  @Test
  void addFireStation_shouldAddFireStationToList() {
    FireStation fireStation = new FireStation(
            "456 Avenue",
            2);
    dataBindingDto.getFirestations().add(fireStation);
    assertTrue(dataBindingDto.getFirestations().contains(fireStation));
  }

  /**
   * Verifies that a medical record can be added to the medical records list.
   */
  @Test
  void addMedicalRecord_shouldAddMedicalRecordToList() {
    ArrayList<String> medications = new ArrayList<>();
    medications.add("med1");
    medications.add("med2");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("allergy1");
    allergies.add("allergy2");
    MedicalRecord medicalRecord = new MedicalRecord(
            "Jane",
            "Doe",
            "02/02/2000",
            medications,
            allergies);
    dataBindingDto.getMedicalrecords().add(medicalRecord);
    assertTrue(dataBindingDto.getMedicalrecords().contains(medicalRecord));
  }
}