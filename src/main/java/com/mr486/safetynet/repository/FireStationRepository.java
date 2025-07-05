package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.FireStation;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing fire station data.
 * Provides methods for CRUD operations and querying fire stations.
 */
public interface FireStationRepository {

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations.
   */
  List<FireStation> getAllFireStations();

  /**
   * Retrieves fire stations by their station number.
   *
   * @param stationNumber the station number to filter by.
   * @return a list of fire stations matching the given station number.
   */
  List<FireStation> getAllFireStationsByStationNumber(Integer stationNumber);

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return the fire station matching the given address, or null if not found.
   */
  Optional<FireStation>  getFireStationByAddress(String address);

  /**
   * Adds a new fire station.
   *
   * @param fireStation the fire station to add.
   */
  void saveFireStation(FireStation fireStation);

  /**
   * Updates an existing fire station.
   *
   * @param fireStation the fire station with updated information.
   */
  void updateFireStation(FireStation fireStation);

  /**
   * Deletes a fire station.
   *
   * @param fireStation the fire station to delete.
   */
  void deletefireStation(FireStation fireStation);
}