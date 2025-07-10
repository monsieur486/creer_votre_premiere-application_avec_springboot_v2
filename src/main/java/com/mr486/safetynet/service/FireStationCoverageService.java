package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FireStationCoverageDto;
import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FireStationCoverageService {

  private final FireStationService fireStationService;
  private final PersonRepository personRepository;
  private final MedicalRecordService medicalRecordService;

  public FireStationCoverageDto getCoverageByStationNumber(Integer stationNumber) {
    List<FireStation> stations = fireStationService.getAllFireStationsByStationNumber(stationNumber);
    List<String> addresses = stations.stream().map(FireStation::getAddress).toList();

    List<FireStationCoverageDto.PersonInfo> personInfos = new ArrayList<>();
    long adultCount = 0;
    long childCount = 0;

    for (String address : addresses) {
      List<Person> personsAtAddress = personRepository.findPersonsByAddress(address);
      for (Person person : personsAtAddress) {
        Optional<MedicalRecord> medicalOpt = medicalRecordService.getMedicalRecordByFirstNameAndLastName(
                new MedicalRecordDto(person.getFirstName(), person.getLastName())
        );

        boolean isAdult = medicalOpt.map(medicalRecordService::isAdult).orElse(true);
        if (isAdult) adultCount++;
        else childCount++;

        personInfos.add(new FireStationCoverageDto.PersonInfo(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        ));
      }
    }

    return new FireStationCoverageDto(personInfos, adultCount, childCount);
  }
}
