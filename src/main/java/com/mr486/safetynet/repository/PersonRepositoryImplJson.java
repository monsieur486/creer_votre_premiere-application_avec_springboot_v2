package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBinding;
import com.mr486.safetynet.dto.search.PersonSearch;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PersonRepository that uses JSON data.
 * This repository loads person data from a JSON file and provides methods
 * to save, update, find, delete, and check existence of persons.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PersonRepositoryImplJson implements PersonRepository {

  /**
   * The JSON data reader used to load fire station data.
   */
  private final JsonDataReader jsonDataReader;

  private List<Person> persons = Collections.emptyList();

  /**
   * Initializes the repository by loading people data from a JSON file.
   * This method is called automatically after the bean is constructed.
   */
  @PostConstruct
  void init() {
    log.warn("Persons data loading from json file");
    try {
      DataBinding dataBinding = jsonDataReader.loadData();
      persons = new java.util.ArrayList<>(dataBinding.getPersons());
      log.warn("✅ Persons data loaded successfully, count: {}", persons.size());
    } catch (Exception e) {
      log.error("❌ {}", e.getMessage());
    }
  }


  /**
   * Saves a person to the repository.
   *
   * @param person the person to save
   */
  @Override
  public void save(Person person) {
    persons.add(person);
  }

  /**
   * Updates an existing person in the repository.
   *
   * @param person the person to update
   */
  @Override
  public void update(Person person) {
    persons.replaceAll(existingPerson ->
            isPersonDtoEqualPerson(existingPerson, new PersonSearch(person.getFirstName(), person.getLastName())) ? person : existingPerson
    );
  }

  /**
   * Finds a person by their first and last name.
   *
   * @param personSearch the DTO containing the first and last name
   * @return an Optional containing the found person, or empty if not found
   */
  @Override
  public Optional<Person> findByFirstNameAndLastName(PersonSearch personSearch) {
    return persons.stream()
            .filter(person -> isPersonDtoEqualPerson(person, personSearch))
            .findFirst();
  }

  /**
   * Deletes a person from the repository.
   *
   * @param personSearch the DTO containing the first and last name of the person to delete
   */
  @Override
  public void delete(PersonSearch personSearch) {
    persons.removeIf(person -> isPersonDtoEqualPerson(person, personSearch));
  }

  /**
   * Checks if a person exists in the repository.
   *
   * @param personSearch the DTO containing the first and last name of the person to check
   * @return true if the person exists, false otherwise
   */
  @Override
  public boolean exists(PersonSearch personSearch) {
    return persons.stream().anyMatch(person -> isPersonDtoEqualPerson(person, personSearch));
  }

  /**
   * Finds all persons living at a specific address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  @Override
  public List<Person> findPersonsByAddress(String address) {
    return persons.stream()
            .filter(person -> person.getAddress().equalsIgnoreCase(address))
            .toList();
  }

  /**
   * Checks if a person matches the given DTO.
   *
   * @param person       the person to check
   * @param personSearch the DTO containing the first and last name
   * @return true if the person matches the DTO, false otherwise
   */
  private boolean isPersonDtoEqualPerson(Person person, PersonSearch personSearch) {
    return person.getFirstName().equals(personSearch.getFirstName()) &&
            person.getLastName().equals(personSearch.getLastName());
  }
}
