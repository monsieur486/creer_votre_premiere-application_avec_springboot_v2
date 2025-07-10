package com.mr486.safetynet.repository;

import com.mr486.safetynet.dto.DataBinding;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link FireStationRepository} that loads and manages fire station data from a JSON file.
 * This repository uses a {@link JsonDataReader} to read fire station data and provides methods to
 * retrieve, save, update, and delete fire station records.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class FireStationRepositoryImplJson implements FireStationRepository {

  /**
   * The JSON data reader used to load fire station data.
   */
  private final JsonDataReader jsonDataReader;

  /**
   * The list of fire stations loaded from the JSON file.
   */
  private List<FireStation> fireStations = Collections.emptyList();

  /**
   * Initializes the repository by loading fire station data from a JSON file.
   * This method is called automatically after the bean is constructed.
   */
  @PostConstruct
  void init() {
    log.warn("Fire stations data loading from json file");
    try {
      DataBinding dataBinding = jsonDataReader.loadData();
      fireStations = new java.util.ArrayList<>(dataBinding.getFirestations());
      log.warn("✅ Fire stations data loaded successfully, count: {}", fireStations.size());
    } catch (Exception e) {
      log.error("❌ {}", e.getMessage());
    }
  }

  /**
   * Retrieves all fire stations with the specified station number.
   *
   * @param stationNumber the station number to filter by
   * @return a list of fire stations with the given station number
   */
  @Override
  public List<FireStation> getAllFireStationsByStationNumber(Integer stationNumber) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getStation().equals(stationNumber))
            .toList();
  }

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station
   * @return an {@link Optional} containing the fire station if found, or empty otherwise
   */
  @Override
  public Optional<FireStation> getFireStationByAddress(String address) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
            .findFirst();
  }

  /**
   * Saves a new fire station to the repository.
   *
   * @param fireStation the fire station to save
   */
  @Override
  public void saveFireStation(FireStation fireStation) {
    fireStations.add(fireStation);
  }

  /**
   * Updates an existing fire station in the repository.
   * The fire station is identified by its address.
   *
   * @param fireStation the fire station with updated information
   */
  @Override
  public void updateFireStation(FireStation fireStation) {
    fireStations.stream()
            .filter(fs -> fs.getAddress().equalsIgnoreCase(fireStation.getAddress()))
            .findFirst()
            .ifPresent(existingFireStation -> {
              existingFireStation.setStation(fireStation.getStation());
            });
  }

  /**
   * Deletes a fire station from the repository by its address.
   *
   * @param address the address of the fire station to delete
   */
  @Override
  public void deleteFireStationByAddress(String address) {
    String addressLowerCase = address.toLowerCase();
    fireStations.removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(addressLowerCase));
  }

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check
   * @return {@code true} if a fire station with the given address exists, {@code false} otherwise
   */
  @Override
  public Boolean existsByAddress(String address) {
    String addressLowerCase = address.toLowerCase();
    return fireStations.stream()
            .anyMatch(fireStation -> fireStation.getAddress().equalsIgnoreCase(addressLowerCase));
  }
}