package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.search.FireStationSearch;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FireStationControllerTest {

  @Mock
  private FireStationService mockFireStationService;

  @InjectMocks
  private FireStationController fireStationController;

  /**
   * Initializes mocks and sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests that the addFireStation method successfully adds a fire station
   * and returns the correct HTTP status code and message.
   */
  @Test
  void addFireStation_shouldAddFireStationSuccessfully() {
    FireStation fireStation = new FireStation("Address1", 1);

    ResponseEntity<String> response = fireStationController.addFireStation(fireStation);

    verify(mockFireStationService, times(1)).saveFireStation(fireStation);
    assertEquals(201, response.getStatusCodeValue());
    assertEquals("Fire station added successfully", response.getBody());
  }

  /**
   * Tests that the updateFireStation method successfully updates a fire station
   * and returns the correct HTTP status code and message.
   */
  @Test
  void updateFireStation_shouldUpdateFireStationSuccessfully() {
    FireStation fireStation = new FireStation("Address1", 1);

    ResponseEntity<String> response = fireStationController.updateFireStation(fireStation);

    verify(mockFireStationService, times(1)).updateFireStation(fireStation);
    assertEquals(201, response.getStatusCodeValue());
    assertEquals("Fire station updated successfully", response.getBody());
  }

  /**
   * Tests that the deleteFireStationByAddress method successfully deletes a fire station
   * by its address and returns the correct HTTP status code and message.
   */
  @Test
  void deleteFireStationByAddress_shouldDeleteFireStationSuccessfully() {
    FireStationSearch fireStation = new FireStationSearch("Address1");
    ResponseEntity<String> response = fireStationController.deleteFireStationByAddress(fireStation);

    verify(mockFireStationService, times(1)).deleteFireStationByAddress(fireStation.getAddress());
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Fire station deleted successfully for address: Address1", response.getBody());
  }

}