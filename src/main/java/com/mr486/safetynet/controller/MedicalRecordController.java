package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.MedicalRecordSearch;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.tools.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing MedicalRecord-related operations.
 * Provides endpoints for CRUD operations on MedicalRecord entities.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

  private final MedicalRecordService medicalRecordService;

  /**
   * Adds a new medical record.
   *
   * @param medicalRecord The MedicalRecord object to be added. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<String> addMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
    medicalRecordService.saveMedicalRecord(medicalRecord);
    return ResponseUtil.created(
            "Medical record added successfully"
    );
  }

  /**
   * Updates an existing medical record.
   *
   * @param medicalRecord The MedicalRecord object with updated information. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @PutMapping(path = "", produces = "application/json")
  public ResponseEntity<String> updateMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
    medicalRecordService.updateMedicalRecord(medicalRecord);
    return ResponseUtil.created(
            "Medical record updated successfully"
    );
  }

  /**
   * Deletes a medical record by its first and last name.
   *
   * @param medicalRecordSearch The MedicalRecordSearch object containing the first and last name of the medical record to be deleted. Must be valid.
   * @return ResponseEntity containing a success message.
   */
  @DeleteMapping(path = "", produces = "application/json")
  public ResponseEntity<String> deleteMedicalRecord(@RequestBody @Valid MedicalRecordSearch medicalRecordSearch) {
    medicalRecordService.deleteMedicalRecord(medicalRecordSearch);
    return ResponseUtil.success(
            "Medical record deleted successfully"
    );
  }
}
