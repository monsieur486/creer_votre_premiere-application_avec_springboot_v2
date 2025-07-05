package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.tools.json_file.DataBinding;
import com.mr486.safetynet.tools.json_file.JsonDataLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
@Slf4j
@RequiredArgsConstructor
public class FireStationRepositoryImplJson implements FireStationRepository {

  private final JsonDataLoader jsonDataLoader;

  private List<FireStation> fireStations = Collections.emptyList();

  @PostConstruct
  void init() {
    log.warn("Fire stations data loading from json file");
    try {
      DataBinding dataBinding = jsonDataLoader.loadData();
      fireStations = new java.util.ArrayList<>(dataBinding.getFirestations());
      log.warn("✅ Fire stations data loaded successfully, count: {}", fireStations.size());
    } catch (Exception e) {
      log.error("❌ {}", e.getMessage());
    }
  }

  @Override
  public List<FireStation> getAllFireStations() {
    return fireStations;
  }

  /**
   * Retrieves all fire stations by their station number.
   *
   * @param stationNumber the station number to filter by.
   * @return a list of fire stations matching the given station number.
   */
  @Override
  public List<FireStation> getAllFireStationsByStationNumber(Integer stationNumber) {
    return fireStations.stream()
            .filter(firestation -> firestation.getStation().equals(stationNumber))
            .toList();
  }

  @Override
  public Optional<FireStation> getFireStationByAddress(String address) {
    return fireStations.stream()
            .filter(firestation -> firestation.getAddress().equals(address))
            .findFirst();
  }


  @Override
  public void saveFireStation(FireStation fireStation) {
    fireStations.add(fireStation);
  }

  @Override
  public void updateFireStation(FireStation fireStation) {
    for (int i = 0; i < fireStations.size(); i++) {
      FireStation existingFirestation = fireStations.get(i);
      if (existingFirestation.getAddress().equals(fireStation.getAddress())) {
        fireStations.set(i, fireStation);
      }
    }
  }

  /**
   * Deletes a fire station.
   *
   * @param fireStation the fire station to delete.
   */
  @Override
  public void deletefireStation(FireStation fireStation) {
    fireStations.remove(fireStation);
  }

  @Override
  public Boolean existsByAddress(String address) {
    return fireStations.stream()
            .anyMatch(firestation -> firestation.getAddress().equals(address));
  }

}