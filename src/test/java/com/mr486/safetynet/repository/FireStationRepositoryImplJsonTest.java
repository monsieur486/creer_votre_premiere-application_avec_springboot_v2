package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBinding;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.tools.JsonDataReader;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link FireStationRepositoryImplJson} class.
 * <p>
 * This test class verifies the correct behavior of the FireStationRepositoryImplJson,
 * including initialization, retrieval, addition, update, deletion, and existence checks
 * for fire station records.
 * </p>
 */
class FireStationRepositoryImplJsonTest {

  /**
   * Mocked instance of {@link JsonDataReader} to simulate JSON data loading.
   */
  @Mock
  private JsonDataReader mockJsonDataReader;

  /**
   * Instance of {@link FireStationRepositoryImplJson} with injected mocks.
   */
  @InjectMocks
  private FireStationRepositoryImplJson fireStationRepository;

  /**
   * Tests that the init method correctly loads fire stations from the JSON file.
   */
  @Test
  void init_shouldLoadFireStationsFromJson() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    DataBinding dataBinding = new DataBinding();
    dataBinding.setFirestations(List.of(station));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    fireStationRepository.init();

    List<FireStation> fireStations = fireStationRepository.getAllFireStationsByStationNumber(1);
    assertEquals(1, fireStations.size());
    assertEquals("Address1", fireStations.get(0).getAddress());
  }

  /**
   * Tests that the init method handles exceptions and keeps the fire stations list empty.
   */
  @Test
  void init_shouldHandleExceptionAndKeepFireStationsEmpty() {
    MockitoAnnotations.openMocks(this);
    when(mockJsonDataReader.loadData()).thenThrow(new RuntimeException("JSON error"));

    fireStationRepository.init();

    Optional<FireStation> result = fireStationRepository.getFireStationByAddress("NonExistentAddress");
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that getAllFireStationsByStationNumber returns an empty list if no stations match the given number.
   */
  @Test
  void getAllFireStationsByStationNumber_shouldReturnEmptyListIfNoMatch() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    when(mockJsonDataReader.loadData()).thenReturn(new DataBinding());

    fireStationRepository.init();
    fireStationRepository.saveFireStation(station);
    List<FireStation> result = fireStationRepository.getAllFireStationsByStationNumber(2);
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that getFireStationByAddress returns an empty Optional if the address does not exist.
   */
  @Test
  void getFireStationByAddress_shouldReturnEmptyIfNotExists() {
    MockitoAnnotations.openMocks(this);
    Optional<FireStation> result = fireStationRepository.getFireStationByAddress("Unknown");
    assertTrue(result.isEmpty());
  }

  /**
   * Tests that saveFireStation adds a new fire station to the repository.
   */
  @Test
  void saveFireStation_shouldAddNewFireStation() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    when(mockJsonDataReader.loadData()).thenReturn(new DataBinding());

    fireStationRepository.init();
    fireStationRepository.saveFireStation(station);

    Optional<FireStation> result = fireStationRepository.getFireStationByAddress("Address1");
    assertTrue(result.isPresent());
  }

  /**
   * Tests that updateFireStation modifies the station number of an existing fire station.
   */
  @Test
  void updateFireStation_shouldModifyStationNumberIfExists() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    when(mockJsonDataReader.loadData()).thenReturn(new DataBinding());
    fireStationRepository.init();
    fireStationRepository.saveFireStation(station);

    FireStation updatedStation = new FireStation("Address1", 2);
    fireStationRepository.updateFireStation(updatedStation);
    Optional<FireStation> result = fireStationRepository.getFireStationByAddress("Address1");
    assertTrue(result.isPresent());
    assertEquals(2, result.get().getStation());
  }

  /**
   * Tests that updateFireStation does nothing if the address does not exist.
   */
  @Test
  void updateFireStation_shouldDoNothingIfAddressNotExists() {
    MockitoAnnotations.openMocks(this);
    fireStationRepository.updateFireStation(new FireStation("Unknown", 7));
    assertFalse(fireStationRepository.existsByAddress("Unknown"));
  }

  /**
   * Tests that deleteFireStationByAddress removes a fire station from the repository.
   */
  @Test
  void deleteFireStationByAddress_shouldRemoveFireStation() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    DataBinding dataBinding = new DataBinding();
    dataBinding.setFirestations(List.of(station));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);

    fireStationRepository.init();
    fireStationRepository.deleteFireStationByAddress("Address1");
    assertFalse(fireStationRepository.existsByAddress("Address1"));
  }

  /**
   * Tests that deleteFireStationByAddress does nothing if the address does not exist.
   */
  @Test
  void deleteFireStationByAddress_shouldDoNothingIfAddressNotExists() {
    MockitoAnnotations.openMocks(this);
    FireStation station = new FireStation("Address1", 1);
    DataBinding dataBinding = new DataBinding();
    dataBinding.setFirestations(List.of(station));
    when(mockJsonDataReader.loadData()).thenReturn(dataBinding);
    fireStationRepository.init();
    fireStationRepository.deleteFireStationByAddress("NonExistentAddress");
    assertTrue(fireStationRepository.existsByAddress("Address1"));
  }

  /**
   * Tests that existsByAddress returns false if the fire station does not exist.
   */
  @Test
  void existsByAddress_shouldReturnFalseIfFireStationDoesNotExist() {
    MockitoAnnotations.openMocks(this);
    assertFalse(fireStationRepository.existsByAddress("NonExistent"));
  }
}