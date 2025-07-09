package com.mr486.safetynet.controller;


import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the PersonController class.
 * This class tests the addPerson, updatePerson, and deletePerson methods of the PersonController.
 */
class PersonControllerTest {

  @Mock
  private PersonService mockPersonService;

  @InjectMocks
  private PersonController personController;

  /**
   * Initializes mocks and sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests the addPerson method of the PersonController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void addPerson_shouldAddPersonSuccessfully() throws Exception {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");

    ResponseEntity<String> responseEntity = personController.addPerson(person);
    // Verify that the service method was called
    verify(mockPersonService, times(1)).savePerson(person);
    // Verify the response
    assertEquals(201, responseEntity.getStatusCodeValue());
    assertEquals("Person added successfully", responseEntity.getBody());

  }

  /**
   * Tests the updatePerson method of the PersonController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void updatePerson_shouldUpdatePersonSuccessfully() throws Exception {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");

    ResponseEntity<String> responseEntity = personController.updatePerson(person);
    // Verify that the service method was called
    verify(mockPersonService, times(1)).updatePerson(person);
    // Verify the response
    assertEquals(201, responseEntity.getStatusCodeValue());
    assertEquals("Person updated successfully", responseEntity.getBody());
  }

  /**
   * Tests the deletePerson method of the PersonController.
   * It verifies that the service method is called and checks the response.
   */
  @Test
  void deletePerson_shouldDeletePersonSuccessfully() throws Exception {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    PersonDto personDto = new PersonDto(person);

    ResponseEntity<String> responseEntity = personController.deletePerson(personDto);
    // Verify that the service method was called
    verify(mockPersonService, times(1)).deletePerson(personDto);
    // Verify the response
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertEquals("Person deleted successfully", responseEntity.getBody());
  }

}