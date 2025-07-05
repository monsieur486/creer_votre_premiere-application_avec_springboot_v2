package com.mr486.safetynet.controller;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.tools.network.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
@Slf4j
public class FireStationController {

  private final FireStationService fireStationService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse<List<FireStation>>> getAllFireStations(){
    ApiResponse<List<FireStation>> response = new ApiResponse<>();
    response.setData(fireStationService.getAllFireStations());
    response.setMessage("All fire stations list");
    response.setStatus(HttpStatus.OK);
    return response.createResponse();
  }

  @PostMapping("/")
  public ResponseEntity<ApiResponse<FireStation>> addFireStation(@RequestBody FireStation fireStation){
    ApiResponse<FireStation> response = new ApiResponse<>();
    fireStationService.saveFireStation(fireStation);
    response.setData(fireStation);
    response.setMessage("Fire station saved successfully");
    response.setStatus(HttpStatus.CREATED);
    return response.createResponse();
  }

}
