package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.AppConfiguration;
import com.mr486.safetynet.dto.response.ChildAlertResponse;
import com.mr486.safetynet.dto.response.ChildDto;
import com.mr486.safetynet.dto.response.OtherHouseholdMemberDto;
import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for handling child alert operations.
 * Provides methods to retrieve children and other household members at a given address.
 */
@Service
@RequiredArgsConstructor
public class ChildAlertService {
  private final PersonRepository personRepository;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves children and other household members at a given address.
   *
   * @param address the address to search for persons
   * @return an Optional containing ChildAlertResponse with lists of children and other household members,
   *         or empty if no children are found
   */
  public Optional<ChildAlertResponse> getChildrenAtAddress(String address) {
    List<Person> persons = personRepository.findPersonsByAddress(address);

    List<ChildDto> children = new ArrayList<>();
    List<OtherHouseholdMemberDto> otherMembers = new ArrayList<>();

    // Iterate through the persons at the address
    for (Person person : persons) {
      Optional<MedicalRecord> recordOpt = medicalRecordService.getMedicalRecordByFirstNameAndLastName(
              new MedicalRecordSearch(person.getFirstName(), person.getLastName())
      );

      // If a medical record exists for the person, calculate their age
      if (recordOpt.isPresent()) {
        MedicalRecord record = recordOpt.get();
        int age = medicalRecordService.calculateAge(record.getBirthdate());

        // Classify the person as a child or other household member based on age
        if (age <= AppConfiguration.AGE_ADULT) {
          children.add(new ChildDto(person.getFirstName(), person.getLastName(), age));
        } else {
          otherMembers.add(new OtherHouseholdMemberDto(
                  person.getFirstName(), person.getLastName()
          ));
        }
      }
    }

    // If no children are found, return an empty Optional
    if (children.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(new ChildAlertResponse(children, otherMembers));
  }
}
