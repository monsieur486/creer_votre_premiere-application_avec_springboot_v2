package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.FireStationCoverageDto;
import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.service.FireStationCoverageService;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to fire station coverage.
 * Provides endpoints to retrieve coverage information based on fire station numbers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationCoverageController {

  private final FireStationCoverageService fireStationCoverageService;

  /**
   * Retrieves the coverage information for a specific fire station.
   *
   * @param stationNumber the number of the fire station to retrieve coverage for
   * @return ResponseEntity containing FireStationCoverageDto with coverage details
   */
  @GetMapping
  public ResponseEntity<FireStationCoverageDto> getCoverageByStation(@RequestParam Integer stationNumber) {
    FireStationCoverageDto coverage = fireStationCoverageService.getCoverageByStationNumber(stationNumber);

    if (coverage.getPersons().isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(coverage);
  }




}
