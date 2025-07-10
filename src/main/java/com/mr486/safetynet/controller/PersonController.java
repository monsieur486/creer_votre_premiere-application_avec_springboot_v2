package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.search.PersonSearch;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import com.mr486.safetynet.tools.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing Person-related operations.
 * Provides endpoints for CRUD operations on Person entities.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

  private final PersonService personService;

  /**
   * Adds a new person.
   *
   * @param person The Person object to be added. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<String> addPerson(@RequestBody @Valid Person person) {
    personService.savePerson(person);
    return ResponseUtil.created(
            "Person added successfully"
    );
  }

  /**
   * Updates an existing person.
   *
   * @param person The Person object with updated information. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PutMapping(path = "", produces = "application/json")
  public ResponseEntity<String> updatePerson(@RequestBody @Valid Person person) {
    personService.updatePerson(person);
    return ResponseUtil.created(
            "Person updated successfully"
    );
  }

  /**
   * Deletes a person by their first and last name.
   *
   * @param personSearch The PersonSearch object containing the first and last name of the person to be deleted. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @DeleteMapping(path = "", produces = "application/json")
  public ResponseEntity<String> deletePerson(@RequestBody @Valid PersonSearch personSearch) {
    personService.deletePerson(personSearch);
    return ResponseUtil.success(
            "Person deleted successfully"
    );
  }
}
