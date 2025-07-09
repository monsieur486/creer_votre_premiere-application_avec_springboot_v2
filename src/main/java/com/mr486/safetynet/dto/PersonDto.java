package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.Person;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing a person.
 * This DTO contains the first and last name of a person and is used for data validation and transfer
 * between layers of the application.
 */
@Data
@AllArgsConstructor
public class PersonDto {

  /**
   * The person's first name.
   */
  @NotBlank(message = "firstname cannot be blank")
  private String firstName;

  /**
   * The person's last name.
   */
  @NotBlank(message = "lastname cannot be blank")
  private String lastName;

  /**
   * Default constructor for PersonDto.
   * Initializes an empty PersonDto.
   * @param person the Person object to initialize the DTO from.
   */
  public PersonDto(Person person) {
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
  }

}
