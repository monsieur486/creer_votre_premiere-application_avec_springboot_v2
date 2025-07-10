package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.search.PersonSearch;
import com.mr486.safetynet.model.Person;

import java.util.List;
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
   * @param personSearch the DTO containing the first and last name
   * @return an Optional containing the found person, or empty if not found
   */
  Optional<Person> findByFirstNameAndLastName(PersonSearch personSearch);

  /**
   * Deletes a person from the repository.
   *
   * @param personSearch the DTO containing the first and last name of the person to delete
   */
  void delete(PersonSearch personSearch);

  /**
   * Checks if a person exists in the repository.
   *
   * @param personSearch the DTO containing the first and last name of the person to check
   * @return true if the person exists, false otherwise
   */
  boolean exists(PersonSearch personSearch);

  /**
   * Finds all persons living at a specific address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  List<Person> findPersonsByAddress(String address);
}
