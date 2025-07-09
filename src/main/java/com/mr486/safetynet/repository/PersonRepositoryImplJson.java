package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.model.Person;

import java.util.Optional;

public class PersonRepositoryImplJson implements PersonRepository {
  @Override
  public void save(Person person) {

  }

  @Override
  public void update(Person person) {

  }

  @Override
  public Optional<Person> findByFirstNameAndLastName(PersonDto personDto) {
    return Optional.empty();
  }

  @Override
  public void delete(PersonDto personDto) {

  }
}
