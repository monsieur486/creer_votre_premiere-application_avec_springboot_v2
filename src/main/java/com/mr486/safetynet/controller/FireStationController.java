package com.mr486.safetynet.controller;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.tools.network.ApiResponse;
import com.mr486.safetynet.tools.network.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

  private final FireStationService fireStationService;

  public FireStationController(FireStationService fireStationService) {
    this.fireStationService = fireStationService;
  }


  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<FireStation>>> getAllFirestations() {
    List<FireStation> fireStations = fireStationService.getAllFireStations();
    return ResponseEntity.ok(
            ResponseUtil.success(
                    "Fire stations retrieved successfully",
                    fireStations));
  }


  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<FireStation>> addFirestation(@Valid @RequestBody FireStation fireStation) {
    FireStation addedFirestation = fireStationService.addFireStation(fireStation);
    return new ResponseEntity<>(
            ResponseUtil.created("Firestation added successfully", addedFirestation),
            HttpStatus.CREATED
    );
  }
}
