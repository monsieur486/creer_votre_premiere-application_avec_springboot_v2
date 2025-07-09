package com.mr486.safetynet.service;

import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for FireStationService.
 * Contains unit tests to verify the behavior of the service methods.
 */
class FireStationServiceTest {

  @Mock
  private FireStationRepository mockFireStationRepository;

  @InjectMocks
  private FireStationService fireStationService;

  /**
   * Verifies that getFireStation returns an existing fire station for a given address.
   */
  @Test
  void getFireStation_shouldReturnFireStationWhenAddressExists() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.getFireStationByAddress("Address1")).thenReturn(Optional.of(fireStation));

    Optional<FireStation> result = fireStationService.getFireStationByAdress("Address1");

    assertTrue(result.isPresent());
    assertEquals(fireStation, result.get());
  }

  /**
   * Verifies that getFireStation returns empty if the address does not exist.
   */
  @Test
  void getFireStation_shouldReturnEmptyWhenAddressDoesNotExist() {
    MockitoAnnotations.openMocks(this);
    when(mockFireStationRepository.getFireStationByAddress("NonExistentAddress")).thenReturn(Optional.empty());

    Optional<FireStation> result = fireStationService.getFireStationByAdress("NonExistentAddress");

    assertTrue(result.isEmpty());
  }

  /**
   * Verifies that saveFireStation throws an exception if the fire station already exists.
   */
  @Test
  void saveFireStation_shouldThrowExceptionWhenFireStationAlreadyExists() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.existsByAddress("Address1")).thenReturn(true);

    EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
      fireStationService.saveFireStation(fireStation);
    });

    assertEquals("fire station with address [Address1] already exists !", exception.getMessage());
  }

  /**
   * Verifies that saveFireStation saves a new fire station if it does not exist.
   */
  @Test
  void saveFireStation_shouldSaveFireStationWhenItDoesNotExist() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.existsByAddress("Address1")).thenReturn(false);

    fireStationService.saveFireStation(fireStation);

    verify(mockFireStationRepository, times(1)).saveFireStation(fireStation);
  }

  /**
   * Verifies that updateFireStation throws an exception if the fire station does not exist.
   */
  @Test
  void updateFireStation_shouldThrowExceptionWhenFireStationDoesNotExist() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.existsByAddress("Address1")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
      fireStationService.updateFireStation(fireStation);
    });

    assertEquals("fire station with address [Address1] does not exist !", exception.getMessage());
  }

  /**
   * Verifies that updateFireStation updates an existing fire station.
   */
  @Test
  void updateFireStation_shouldUpdateFireStationWhenItExists() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.existsByAddress("Address1")).thenReturn(true);

    fireStationService.updateFireStation(fireStation);

    verify(mockFireStationRepository, times(1)).updateFireStation(fireStation);
  }


  /**
   * Verifies that deleteFireStation deletes an existing fire station.
   */
  @Test
  void deleteFireStationByAdress_shouldDeleteFireStationWhenItExists() {
    MockitoAnnotations.openMocks(this);
    FireStation fireStation = new FireStation("Address1", 1);
    when(mockFireStationRepository.existsByAddress("Address1")).thenReturn(true);

    fireStationService.deleteFireStationByAddress("Address1");

    verify(mockFireStationRepository, times(1)).deleteFireStationByAddress("Address1");
  }

  /**
   * Verifies that deleteFireStationByAddress throws an exception if the fire station does not exist.
   */
  @Test
  void deleteFireStationByAddress_shouldDeleteFireStationWhenItExists() {
    MockitoAnnotations.openMocks(this);
    String address = "Address1";
    when(mockFireStationRepository.existsByAddress(address)).thenReturn(true);

    fireStationService.deleteFireStationByAddress(address);

    verify(mockFireStationRepository, times(1)).deleteFireStationByAddress(address);
  }

  /**
   * Verifies that deleteFireStationByAddress throws an exception if the fire station does not exist.
   */
  @Test
  void deleteFireStationByAddress_shouldThrowExceptionWhenFireStationDoesNotExist() {
    MockitoAnnotations.openMocks(this);
    String address = "NonExistentAddress";
    when(mockFireStationRepository.existsByAddress(address)).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
      fireStationService.deleteFireStationByAddress(address);
    });

    assertEquals("fire station with address [NonExistentAddress] does not exist !", exception.getMessage());
  }

  /**
   * Verifies that getAllFireStationsByStationNumber returns all fire stations for a given station number.
   */
  @Test
  void getAllFireStationsByStationNumber_shouldReturnAllFireStationsForGivenStationNumber() {
    MockitoAnnotations.openMocks(this);
    int stationNumber = 1;
    FireStation fireStation1 = new FireStation("Address1", stationNumber);
    FireStation fireStation2 = new FireStation("Address2", stationNumber);
    when(mockFireStationRepository.getAllFireStationsByStationNumber(stationNumber))
            .thenReturn(List.of(fireStation1, fireStation2));

    List<FireStation> result = fireStationService.getAllFireStationsByStationNumber(1);

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }
}