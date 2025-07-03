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

class FireStationRepositoryImplJsonTest {

  @Mock
  private JsonDataLoader jsonDataLoader;

  @InjectMocks
  private FireStationRepositoryImplJson repository;

  private final List<FireStation> mockFirestations = List.of(
          new FireStation("123 Rue A", 1),
          new FireStation("456 Rue B", 2)
  );

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

  @Test
  void getAllFireStations_shouldReturnAll() {
    List<FireStation> result = repository.getAllFireStations();
    assertEquals(2, result.size());
  }

  @Test
  void getFireStationByAddress_existingAddress_shouldReturnMatch() {
    FireStation result = repository.getFireStationByAddress("123 Rue A");
    assertNotNull(result);
    assertEquals(1, result.getStation());
  }

  @Test
  void getFireStationByAddress_nonExistingAddress_shouldReturnNull() {
    FireStation result = repository.getFireStationByAddress("Rue Inconnue");
    assertNull(result);
  }

  @Test
  void getAllFireStationsByStationNumber_existingNumber_shouldReturnFilteredList() {
    List<FireStation> result = repository.getAllFireStationsByStationNumber(2);
    assertEquals(1, result.size());
    assertEquals("456 Rue B", result.get(0).getAddress());
  }

  @Test
  void addfireStation_new_shouldAddSuccessfully() {
    FireStation newFs = new FireStation("789 Rue C", 3);
    FireStation result = repository.addfireStation(newFs);
    assertTrue(repository.getAllFireStations().contains(newFs));
    assertEquals(newFs, result);
  }

  @Test
  void addfireStation_existingAddress_shouldThrowException() {
    FireStation duplicate = new FireStation("123 Rue A", 1);
    assertThrows(IllegalArgumentException.class, () -> repository.addfireStation(duplicate));
  }

  @Test
  void updateFireStation_shouldReplaceData() {
    FireStation updated = new FireStation("123 Rue A", 10);
    FireStation result = repository.updateFireStation(updated);
    assertNotNull(result);
    assertEquals(10, result.getStation());
  }

  @Test
  void deletefireStation_shouldRemove() {
    FireStation toDelete = new FireStation("123 Rue A", 1);
    repository.deletefireStation(toDelete);
    assertFalse(repository.getAllFireStations().contains(toDelete));
  }

  @Test
  void existByAddress_existing_shouldReturnTrue() {
    assertTrue(repository.existByAddress("123 Rue A"));
  }

  @Test
  void existByAddress_nonExisting_shouldReturnFalse() {
    assertFalse(repository.existByAddress("000 Rue Z"));
  }
}