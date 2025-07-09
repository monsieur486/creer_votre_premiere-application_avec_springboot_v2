package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.FireStationRepository;
import com.mr486.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

  private PersonRepository personRepository;
  private PersonService personService;

  @BeforeEach
  void setUp() {
    personRepository = mock(PersonRepository.class);
    personService = new PersonService(personRepository);
  }

  @Test
  void testGetPersonByFirstNameAndLastName_found() {
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    PersonDto dto = new PersonDto(person);
    when(personRepository.findByFirstNameAndLastName(dto)).thenReturn(Optional.of(person));

    Optional<Person> result = personService.getPersonByFirstNameAndLastName(dto);

    assertTrue(result.isPresent());
    verify(personRepository, times(1)).findByFirstNameAndLastName(dto);
  }

  @Test
  void testSavePerson_success() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Smith");

    PersonDto dto = new PersonDto(person);
    when(personRepository.exists(dto)).thenReturn(false);

    personService.savePerson(person);

    verify(personRepository).save(person);
  }

  @Test
  void testSavePerson_alreadyExists() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Smith");

    PersonDto dto = new PersonDto(person);
    when(personRepository.exists(dto)).thenReturn(true);

    assertThrows(EntityAlreadyExistsException.class, () -> personService.savePerson(person));
    verify(personRepository, never()).save(any());
  }

  @Test
  void testUpdatePerson_success() {
    Person person = new Person();
    person.setFirstName("Jake");
    person.setLastName("Johnson");

    PersonDto dto = new PersonDto(person);
    when(personRepository.exists(dto)).thenReturn(true);

    personService.updatePerson(person);

    verify(personRepository).update(person);
  }

  @Test
  void testUpdatePerson_notFound() {
    Person person = new Person();
    person.setFirstName("Jake");
    person.setLastName("Johnson");

    PersonDto dto = new PersonDto(person);
    when(personRepository.exists(dto)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> personService.updatePerson(person));
    verify(personRepository, never()).update(any());
  }

  @Test
  void testDeletePerson_success() {
    PersonDto dto = new PersonDto("John", "Doe");
    when(personRepository.exists(dto)).thenReturn(true);

    personService.deletePerson(dto);

    verify(personRepository).delete(dto);
  }

  @Test
  void testDeletePerson_notFound() {
    PersonDto dto = new PersonDto("John", "Doe");
    when(personRepository.exists(dto)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> personService.deletePerson(dto));
    verify(personRepository, never()).delete(any());
  }





}