package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.FireStationCoverage;
import com.mr486.safetynet.service.FireStationCoverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
   * @return ResponseEntity containing FireStationCoverage with coverage details
   */
  @GetMapping
  public ResponseEntity<FireStationCoverage> getCoverageByStation(@RequestParam Integer stationNumber) {
    FireStationCoverage coverage = fireStationCoverageService.getCoverageByStationNumber(stationNumber);

    if (coverage.getPersons().isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(coverage);
  }


}
