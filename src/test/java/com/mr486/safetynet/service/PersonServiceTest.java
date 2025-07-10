package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.PersonSearch;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test class for PersonService
 */
class PersonServiceTest {

  private PersonRepository personRepository;
  private PersonService personService;

  @BeforeEach
  void setUp() {
    personRepository = mock(PersonRepository.class);
    personService = new PersonService(personRepository);
  }

  /**
   * Test for getting a person by first name and last name
   */
  @Test
  void testGetPersonByFirstNameAndLastName_found() {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    PersonSearch dto = new PersonSearch(person);
    when(personRepository.findByFirstNameAndLastName(dto)).thenReturn(Optional.of(person));

    Optional<Person> result = personService.getPersonByFirstNameAndLastName(dto);

    assertTrue(result.isPresent());
    verify(personRepository, times(1)).findByFirstNameAndLastName(dto);
  }

  /**
   * Test for getting a person by first name and last name when not found
   */
  @Test
  void testSavePerson_success() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Smith");

    PersonSearch dto = new PersonSearch(person);
    when(personRepository.exists(dto)).thenReturn(false);

    personService.savePerson(person);

    verify(personRepository).save(person);
  }

  /**
   * Test for saving a person when already exists
   */
  @Test
  void testSavePerson_alreadyExists() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Smith");

    PersonSearch dto = new PersonSearch(person);
    when(personRepository.exists(dto)).thenReturn(true);

    assertThrows(EntityAlreadyExistsException.class, () -> personService.savePerson(person));
    verify(personRepository, never()).save(any());
  }

  /**
   * Test for updating a person
   */
  @Test
  void testUpdatePerson_success() {
    Person person = new Person();
    person.setFirstName("Jake");
    person.setLastName("Johnson");

    PersonSearch dto = new PersonSearch(person);
    when(personRepository.exists(dto)).thenReturn(true);

    personService.updatePerson(person);

    verify(personRepository).update(person);
  }

  /**
   * Test for updating a person that does not exist
   */
  @Test
  void testUpdatePerson_notFound() {
    Person person = new Person();
    person.setFirstName("Jake");
    person.setLastName("Johnson");

    PersonSearch dto = new PersonSearch(person);
    when(personRepository.exists(dto)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> personService.updatePerson(person));
    verify(personRepository, never()).update(any());
  }

  /**
   * Test for deleting a person
   */
  @Test
  void testDeletePerson_success() {
    PersonSearch dto = new PersonSearch("John", "Doe");
    when(personRepository.exists(dto)).thenReturn(true);

    personService.deletePerson(dto);

    verify(personRepository).delete(dto);
  }

  /**
   * Test for deleting a person that does not exist
   */
  @Test
  void testDeletePerson_notFound() {
    PersonSearch dto = new PersonSearch("John", "Doe");
    when(personRepository.exists(dto)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> personService.deletePerson(dto));
    verify(personRepository, never()).delete(any());
  }


}