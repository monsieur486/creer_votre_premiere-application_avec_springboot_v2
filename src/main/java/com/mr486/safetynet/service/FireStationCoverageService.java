package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.response.FireStationCoverage;
import com.mr486.safetynet.dto.search.MedicalRecordSearch;
import com.mr486.safetynet.dto.response.PersonInfo;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle fire station coverage logic.
 * It retrieves the coverage information for a specific fire station number,
 * including the list of persons covered and their age distribution.
 */
@Service
@RequiredArgsConstructor
public class FireStationCoverageService {

  private final FireStationService fireStationService;
  private final PersonRepository personRepository;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves the coverage information for a specific fire station number.
   * It returns a DTO containing the list of persons covered by the fire station,
   * along with counts of adults and children.
   *
   * @param stationNumber the fire station number to get coverage for
   * @return FireStationCoverage containing person information and counts
   */
  public FireStationCoverage getCoverageByStationNumber(Integer stationNumber) {
    List<FireStation> stations = fireStationService.getAllFireStationsByStationNumber(stationNumber);
    List<String> addresses = stations.stream().map(FireStation::getAddress).toList();

    List<PersonInfo> personInfos = new ArrayList<>();
    long adultCount = 0;
    long childCount = 0;

    // Iterate through each address and gather person information
    for (String address : addresses) {
      // Find all persons at the current address
      List<Person> personsAtAddress = personRepository.findPersonsByAddress(address);
      // For each person, check their medical record to determine if they are an adult or a child
      for (Person person : personsAtAddress) {
        Optional<MedicalRecord> medicalOpt = medicalRecordService.getMedicalRecordByFirstNameAndLastName(
                new MedicalRecordSearch(person.getFirstName(), person.getLastName())
        );

        // Determine if the person is an adult or a child based on their medical record
        boolean isAdult = medicalOpt.map(medicalRecordService::isAdult).orElse(true);
        if (isAdult) adultCount++;
        else childCount++;

        // Add the person's information to the list
        PersonInfo personInfo = new PersonInfo();
        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAddress(person.getAddress());
        personInfo.setPhone(person.getPhone());
        personInfos.add(personInfo);
      }
    }

    return new FireStationCoverage(personInfos, adultCount, childCount);
  }
}
