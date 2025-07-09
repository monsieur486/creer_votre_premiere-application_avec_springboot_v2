package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing Person entities.
 * Provides methods for CRUD operations and business logic related to persons.
 */
@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  /**
   * Retrieves a person by their first name and last name.
   *
   * @param personDto the DTO containing the first name and last name of the person to retrieve.
   * @return an Optional containing the Person if found, or empty otherwise.
   */
  public Optional<Person> getPersonByFirstNameAndLastName(PersonDto personDto) {
    return personRepository.findByFirstNameAndLastName(personDto);
  }

  /**
   * Saves a new person.
   * Throws an exception if a person with the same first name and last name already exists.
   *
   * @param person the Person entity to save.
   */
  public void savePerson(Person person) {
    PersonDto personDto = new PersonDto(person.getFirstName(), person.getLastName());
    if (personRepository.exists(personDto)) {
      String message = "Person with first name [" + person.getFirstName() + "] and last name [" + person.getLastName() + "] already exists!";
      throw new EntityAlreadyExistsException(message);
    }
    personRepository.save(person);
  }

  /**
   * Updates an existing person.
   * Throws an exception if the person does not exist.
   *
   * @param person the Person entity with updated information.
   */
  public void updatePerson(Person person) {
    PersonDto personDto = new PersonDto(person.getFirstName(), person.getLastName());
    if (!personRepository.exists(personDto)) {
      String message = "Person with first name [" + person.getFirstName() + "] and last name [" + person.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    personRepository.update(person);
  }

  /**
   * Deletes a person.
   * Throws an exception if the person does not exist.
   *
   * @param personDto the DTO containing the first name and last name of the person to delete.
   */
  public void deletePerson(PersonDto personDto) {
    if (!personRepository.exists(personDto)) {
      String message = "Person with first name [" + personDto.getFirstName() + "] and last name [" + personDto.getLastName() + "] does not exist!";
      throw new EntityNotFoundException(message);
    }
    personRepository.delete(personDto);
  }
}
