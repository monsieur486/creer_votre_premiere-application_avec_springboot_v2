package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBindingDto;
import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.tools.JsonDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the PersonRepositoryImplJson class.
 * This test class verifies the functionality of the repository methods
 * such as saving, updating, finding, deleting, and checking existence of Person objects.
 */
class PersonRepositoryImplJsonTest {

  @Mock
  private JsonDataReader mockJsonDataReader; // Mocked JsonDataReader to simulate data loading.

  @InjectMocks
  private PersonRepositoryImplJson personRepository; // Repository under test.

  /**
   * Initializes mocks and sets up the repository before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Verifies that the repository correctly loads persons from JSON data during initialization.
   */
  @Test
  void init_shouldLoadPersonsFromJson() {
    Person person = new Person("John",
            "Doe",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    DataBindingDto dataBinding = new DataBindingDto();
    dataBinding.setPersons(List.of(person));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    personRepository.init();

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  /**
   * Verifies that the repository initializes with an empty list of persons if no data is loaded.
   */
  @Test
  void init_shouldHandleExceptionAndKeepFireStationsEmpty(){
    MockitoAnnotations.openMocks(this);
    when(mockJsonDataReader.loadData()).thenThrow(new RuntimeException("JSON error"));

    personRepository.init();

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("NonExistent", "Person"));
    assertTrue(result.isEmpty());
  }

  /**
   * Verifies that a person can be successfully saved to the repository.
   */
  @Test
  void savesPersonSuccessfully() {
    Person person = new Person("John",
            "Doe",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
  }

  /**
   * Verifies that an existing person can be updated in the repository.
   */
  @Test
  void updatesExistingPerson() {
    Person person = new Person("John",
            "Doe",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    Person updatedPerson = new Person("John",
            "Doe",
            "New Address",
            "Cityville",
            "98765",
            "123-456-7890",
            "john.doe@test.com"
    );
    personRepository.update(updatedPerson);

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("New Address", result.get().getAddress());
  }

  /**
   * Verifies that a person can be found by their first and last name in the repository.
   */
  @Test
  void findsPersonByFirstAndLastName() {
    Person person = new Person("Jane",
            "Smith",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "jane.smith@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("Jane", "Smith"));
    assertTrue(result.isPresent());
    assertEquals("Jane", result.get().getFirstName());
    assertEquals("Smith", result.get().getLastName());
  }

  /**
   * Verifies that a person can be successfully deleted from the repository.
   */
  @Test
  void deletesPersonSuccessfully() {
    Person person = new Person("Alice",
            "Brown",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    personRepository.delete(new PersonDto("Alice", "Brown"));

    assertFalse(personRepository.exists(new PersonDto("Alice", "Brown")));
  }

  /**
   * Verifies that deleting a non-existent person does not cause errors.
   */
  @Test
  void handlesNonExistentPersonDeletionGracefully() {
    personRepository.delete(new PersonDto("Nonexistent", "Person"));

    assertFalse(personRepository.exists(new PersonDto("Nonexistent", "Person")));
  }

  /**
   * Verifies the existence check functionality for persons in the repository.
   */
  @Test
  void checksExistenceOfPerson() {
    Person person = new Person("Bob",
            "White",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    assertTrue(personRepository.exists(new PersonDto("Bob", "White")));
    assertFalse(personRepository.exists(new PersonDto("Nonexistent", "Person")));
  }

  /**
   * Verifies that finding a person by first and last name returns empty if the person does not exist.
   */
  @Test
  void findByFirstNameAndLastName_shouldReturnEmptyIfLastNameNotMatch() {
    Person person = new Person("John",
            "Doe",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);
    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("John", "Smith"));
    assertTrue(result.isEmpty(), "Expected no person found with first name 'John' and last name 'Smith'");

  }

  /**
   * Verifies that updating a person does not change their details if the person is not found.
   */
  @Test
  void update_shouldNotChangePersonIfNotFound() {
    Person person = new Person("John",
            "Doe",
            "123 Main St",
            "Cityville",
            "12345",
            "123-456-7890",
            "john.doe@test.com"
    );
    when(mockJsonDataReader.loadData()).thenReturn(new DataBindingDto());
    personRepository.init();
    personRepository.save(person);

    Person updatedPerson = new Person("Alice",
            "Doe",
            "New Address",
            "Cityville",
            "98765",
            "123-456-7890",
            "john.doe@test.com"
    );
    personRepository.update(updatedPerson);

    Optional<Person> result = personRepository.findByFirstNameAndLastName(new PersonDto("John", "Doe"));
    assertTrue(result.isPresent());
    assertEquals("123 Main St", result.get().getAddress(), "Expected address to remain unchanged");
  }
}