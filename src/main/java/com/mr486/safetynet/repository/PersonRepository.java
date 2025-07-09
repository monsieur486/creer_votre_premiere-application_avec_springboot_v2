package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.model.Person;

import java.util.Optional;

/**
 * Repository interface for managing Person entities.
 * Provides methods to save, update, find, and delete persons.
 */
public interface PersonRepository {
  /**
   * Saves a person to the repository.
   *
   * @param person the person to save
   */
  void save(Person person);

  /**
   * Updates an existing person in the repository.
   *
   * @param person the person to update
   */
  void update(Person person);


  /**
   * Finds a person by their first and last name.
   *
   * @param personDto the DTO containing the first and last name
   * @return an Optional containing the found person, or empty if not found
   */
  Optional<Person> findByFirstNameAndLastName(PersonDto personDto);

  /**
   * Deletes a person from the repository.
   *
   * @param personDto the DTO containing the first and last name of the person to delete
   */
  void delete(PersonDto personDto);

  /**
   * Checks if a person exists in the repository.
   *
   * @param personDto the DTO containing the first and last name of the person to check
   * @return true if the person exists, false otherwise
   */
  boolean exists(PersonDto personDto);
}
