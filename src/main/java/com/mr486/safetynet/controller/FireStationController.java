package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.search.FireStationSearch;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.tools.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing FireStation-related operations.
 * Provides endpoints for CRUD operations on FireStation entities.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {

  private final FireStationService fireStationService;

  /**
   * Adds a new fire station.
   *
   * @param fireStation The FireStation object to be added. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<String> addFireStation(@RequestBody @Valid FireStation fireStation) {
    fireStationService.saveFireStation(fireStation);
    return ResponseUtil.created(
            "Fire station added successfully"
    );
  }

  /**
   * Updates an existing fire station.
   *
   * @param fireStation The FireStation object with updated information. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PutMapping(path = "", produces = "application/json")
  public ResponseEntity<String> updateFireStation(@RequestBody @Valid FireStation fireStation) {
    fireStationService.updateFireStation(fireStation);
    return ResponseUtil.created(
            "Fire station updated successfully"
    );
  }

  /**
   * Deletes a fire station by its address.
   *
   * @param fireStation The FireStation object with updated information. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @DeleteMapping(path = "", produces = "application/json")
  public ResponseEntity<String> deleteFireStationByAddress(@RequestBody @Valid FireStationSearch fireStation) {
    fireStationService.deleteFireStationByAddress(fireStation.getAddress());
    return ResponseUtil.success(
            "Fire station deleted successfully for address: " + fireStation.getAddress()
    );
  }
}
