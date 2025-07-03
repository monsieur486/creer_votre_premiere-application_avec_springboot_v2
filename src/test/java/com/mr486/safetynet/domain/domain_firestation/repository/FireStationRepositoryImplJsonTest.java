package com.mr486.safetynet.domain.domain_firestation.repository;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;
import com.mr486.safetynet.tools.DataBinding;
import com.mr486.safetynet.tools.JsonDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the FireStationRepositoryImplJson class.
 * This class uses Mockito to mock dependencies and test the repository's behavior.
 */
public class FireStationRepositoryImplJsonTest {

  private final List<FireStation> mockFirestations = List.of(
          new FireStation("123 Rue A", 1),
          new FireStation("456 Rue B", 2)
  );
  @Mock
  private JsonDataLoader jsonDataLoader; // Mocked dependency for loading JSON data.
  @InjectMocks
  private FireStationRepositoryImplJson repository; // Repository under test.

  /**
   * Sets up the test environment before each test.
   * Initializes mocks and injects them into the repository.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    DataBinding mockDataBinding = new DataBinding();
    mockDataBinding.setFirestations(mockFirestations);

    String DATA_PATH = "test-data.json";
    when(jsonDataLoader.loadData(DATA_PATH)).thenReturn(mockDataBinding);

    repository = new FireStationRepositoryImplJson(jsonDataLoader);
    repository.dataFilePath = DATA_PATH;

    repository.init();
  }

  /**
   * Tests that getAllFireStations() returns all fire stations.
   */
  @Test
  void getAllFireStations_shouldReturnAll() {
    List<FireStation> result = repository.getAllFireStations();
    assertEquals(2, result.size());
  }

  /**
   * Tests that getFireStationByAddress() returns the correct fire station for an existing address.
   */
  @Test
  void getFireStationByAddress_existingAddress_shouldReturnMatch() {
    FireStation result = repository.getFireStationByAddress("123 Rue A");
    assertNotNull(result);
    assertEquals(1, result.getStation());
  }

  /**
   * Tests that getFireStationByAddress() returns null for a non-existing address.
   */
  @Test
  void getFireStationByAddress_nonExistingAddress_shouldReturnNull() {
    FireStation result = repository.getFireStationByAddress("Rue Inconnue");
    assertNull(result);
  }

  /**
   * Tests that getAllFireStationsByStationNumber() filters fire stations by station number.
   */
  @Test
  void getAllFireStationsByStationNumber_existingNumber_shouldReturnFilteredList() {
    List<FireStation> result = repository.getAllFireStationsByStationNumber(2);
    assertEquals(1, result.size());
    assertEquals("456 Rue B", result.get(0).getAddress());
  }

  /**
   * Tests that addfireStation() successfully adds a new fire station.
   */
  @Test
  void addfireStation_new_shouldAddSuccessfully() {
    FireStation newFs = new FireStation("789 Rue C", 3);
    FireStation result = repository.addfireStation(newFs);
    assertTrue(repository.getAllFireStations().contains(newFs));
    assertEquals(newFs, result);
  }

  /**
   * Tests that addfireStation() throws an exception when adding a fire station with an existing address.
   */
  @Test
  void addfireStation_existingAddress_shouldThrowException() {
    FireStation duplicate = new FireStation("123 Rue A", 1);
    assertThrows(IllegalArgumentException.class, () -> repository.addfireStation(duplicate));
  }

  /**
   * Tests that updateFireStation() updates the data of an existing fire station.
   */
  @Test
  void updateFireStation_shouldReplaceData() {
    FireStation updated = new FireStation("123 Rue A", 10);
    FireStation result = repository.updateFireStation(updated);
    assertNotNull(result);
    assertEquals(10, result.getStation());
  }

  /**
   * Tests that deletefireStation() removes a fire station from the repository.
   */
  @Test
  void deletefireStation_shouldRemove() {
    FireStation toDelete = new FireStation("123 Rue A", 1);
    repository.deletefireStation(toDelete);
    assertFalse(repository.getAllFireStations().contains(toDelete));
  }

  /**
   * Tests that existByAddress() returns true for an existing address.
   */
  @Test
  void existByAddress_existing_shouldReturnTrue() {
    assertTrue(repository.existByAddress("123 Rue A"));
  }

  /**
   * Tests that existByAddress() returns false for a non-existing address.
   */
  @Test
  void existByAddress_nonExisting_shouldReturnFalse() {
    assertFalse(repository.existByAddress("000 Rue Z"));
  }

  /**
   * Tests that passing null to getAllFireStationsByStationNumber throws an exception.
   */
  @Test
  void getAllFireStationsByStationNumber_null_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.getAllFireStationsByStationNumber(null));
    assertEquals("Station number must not be null", exception.getMessage());
  }

  /**
   * Tests that passing null to getFireStationByAddress throws an exception.
   */
  @Test
  void getFireStationByAddress_null_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.getFireStationByAddress(null));
    assertEquals("Adress must not be blank", exception.getMessage());
  }

  /**
   * Tests that passing a blank address to getFireStationByAddress throws an exception.
   */
  @Test
  void getFireStationByAddress_blank_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.getFireStationByAddress("   "));
    assertEquals("Adress must not be blank", exception.getMessage());
  }

  /**
   * Tests that adding a null fire station throws an exception.
   */
  @Test
  void addfireStation_null_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.addfireStation(null));
    assertEquals("Firestation must not be null", exception.getMessage());
  }

  /**
   * Tests that adding a duplicate fire station throws an exception.
   */
  @Test
  void addfireStation_duplicateAddress_shouldThrowException() {
    FireStation duplicate = new FireStation("123 Rue A", 1);
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.addfireStation(duplicate));
    assertEquals("Firestation with this address already exists: 123 Rue A", exception.getMessage());
  }

  /**
   * Tests that updating a null fire station throws an exception.
   */
  @Test
  void updateFireStation_null_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.updateFireStation(null));
    assertEquals("Firestation must not be null", exception.getMessage());
  }

  /**
   * Tests that deleting a null fire station throws an exception.
   */
  @Test
  void deletefireStation_null_shouldThrowException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
            repository.deletefireStation(null));
    assertEquals("Firestation must not be null", exception.getMessage());
  }
}