package com.mr486.safetynet.domain.domain_firestation.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.domain.domain_firestation.model.FireStation;
import com.mr486.safetynet.tools.DataBinding;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the FireStationRepository interface for managing fire station data.
 * This implementation reads and writes data from/to a JSON file.
 */
@Repository
@Slf4j
public class FireStationRepositoryImplJson implements FireStationRepository {

  private List<FireStation> fireStations = Collections.emptyList();

  /**
   * Initializes the repository by loading fire station data from a JSON file.
   * This method is called automatically after the bean is constructed.
   */
  @PostConstruct
  private void init() {
    log.warn("Data loading from JSON file");
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("data/data.json");

    if (file.exists()) {
      log.warn("File not found : {}", file.getAbsolutePath());
      try {
        // Read the JSON file and map it to the DataBinding class
        DataBinding dataBinding = mapper.readValue(file, DataBinding.class);
        fireStations = dataBinding.getFirestations();
      } catch (IOException e) {
        throw new RuntimeException("Error reading the data file: " + e.getMessage(), e);
      }
    } else {
      log.warn("File not found. Creating an empty list of fire stations.");
    }
  }

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations.
   */
  @Override
  public List<FireStation> getAllFireStations() {
    return fireStations;
  }

  /**
   * Retrieves all fire stations by their station number.
   *
   * @param stationNumber the station number to filter by.
   * @return a list of fire stations matching the given station number.
   * @throws IllegalArgumentException if the station number is null.
   */
  @Override
  public List<FireStation> getAllFireStationsByStationNumber(Integer stationNumber) {
    if (stationNumber == null) {
      throw new IllegalArgumentException("Station number must not be null");
    }

    return fireStations.stream()
            .filter(firestation -> firestation.getStation() != null && firestation.getStation().equals(stationNumber))
            .toList();
  }

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return the fire station matching the given address, or null if not found.
   * @throws IllegalArgumentException if the address is null or blank.
   */
  @Override
  public FireStation getFireStationByAddress(String address) {
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Adress must not be blank");
    }

    return fireStations.stream()
            .filter(firestation -> firestation.getAddress() != null && firestation.getAddress().equals(address))
            .findFirst()
            .orElse(null);
  }

  /**
   * Adds a new fire station.
   *
   * @param fireStation the fire station to add.
   * @return the added fire station.
   * @throws IllegalArgumentException if the fire station is null or already exists.
   */
  @Override
  public FireStation addfireStation(FireStation fireStation) {
    if (fireStation == null) {
      throw new IllegalArgumentException("Firestation must not be null");
    }

    if (existByAddress(fireStation.getAddress())) {
      throw new IllegalArgumentException("Firestation with this address already exists: " + fireStation.getAddress());
    }
    fireStations.add(fireStation);
    return fireStation;
  }

  /**
   * Updates an existing fire station.
   *
   * @param fireStation the fire station with updated information.
   * @return the updated fire station, or null if the fire station does not exist.
   * @throws IllegalArgumentException if the fire station is null.
   */
  @Override
  public FireStation updateFireStation(FireStation fireStation) {
    if (fireStation == null) {
      throw new IllegalArgumentException("Firestation must not be null");
    }

    for (int i = 0; i < fireStations.size(); i++) {
      FireStation existingFirestation = fireStations.get(i);
      if (existingFirestation.getStation().equals(fireStation.getStation()) &&
              existingFirestation.getAddress().equals(fireStation.getAddress())) {
        fireStations.set(i, fireStation);
        return fireStation;
      }
    }
    return null;
  }

  /**
   * Deletes a fire station.
   *
   * @param fireStation the fire station to delete.
   * @throws IllegalArgumentException if the fire station is null.
   */
  @Override
  public void deletefireStation(FireStation fireStation) {
    if (fireStation == null) {
      throw new IllegalArgumentException("Firestation must not be null");
    }

    fireStations.removeIf(existingFirestation ->
            existingFirestation.getStation().equals(fireStation.getStation()) &&
                    existingFirestation.getAddress().equals(fireStation.getAddress()));
  }

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check.
   * @return true if a fire station exists at the given address, false otherwise.
   */
  @Override
  public Boolean existByAddress(String address) {
    if (address == null) {
      return false;
    }

    return fireStations.stream()
            .anyMatch(firestation -> firestation.getAddress() != null && firestation.getAddress().equals(address));
  }
}