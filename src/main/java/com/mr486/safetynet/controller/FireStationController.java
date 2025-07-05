package com.mr486.safetynet.controller;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.tools.network.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
@Slf4j
public class FireStationController {

  private final FireStationService fireStationService;

  @GetMapping(path = "", produces = "application/json")
  public ResponseEntity<List<FireStation>> getAllFireStations() {
    return ResponseUtil.success(fireStationService.getAllFireStations());
  }

  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<String> addFireStation(@RequestBody FireStation fireStation) {
    fireStationService.saveFireStation(fireStation);
    return ResponseUtil.created(
            "Fire station added successfully"
    );
  }

  @PutMapping(path = "", produces = "application/json")
  public ResponseEntity<String> updateFireStation(@RequestBody FireStation fireStation) {
    fireStationService.updateFireStation(fireStation);
    return ResponseUtil.created(
            "Fire station updated successfully"
    );
  }

  @DeleteMapping
  public ResponseEntity<String> deleteFireStation(@RequestBody FireStation fireStation) {
    fireStationService.deleteFireStation(fireStation);
    return ResponseUtil.success(
            "Fire station deleted successfully"
    );
  }

}
