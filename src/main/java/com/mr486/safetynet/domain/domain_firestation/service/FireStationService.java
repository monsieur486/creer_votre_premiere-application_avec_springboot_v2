package com.mr486.safetynet.domain.domain_firestation.service;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;

import java.util.List;

/**
 * Service interface for managing fire stations.
 * Provides methods for CRUD operations and querying fire station data.
 */
public interface FireStationService {

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations
   */
  List<FireStation> getAllFireStations();

  /**
   * Retrieves fire stations by their station number.
   *
   * @param stationNumber the station number to filter by
   * @return a list of fire stations with the specified station number
   */
  List<FireStation> getFireStationsByStationNumber(Integer stationNumber);

  /**
   * Checks if a specific fire station exists.
   *
   * @param fireStation the fire station to check
   * @return true if the fire station exists, false otherwise
   */
  Boolean exists(FireStation fireStation);

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check
   * @return true if a fire station exists at the specified address, false otherwise
   */
  Boolean existsByAddress(String address);

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station
   * @return the fire station at the specified address, or null if not found
   */
  FireStation getFireStationByAddress(String address);

  /**
   * Adds a new fire station.
   *
   * @param fireStation the fire station to add
   * @return the added fire station
   */
  FireStation addFireStation(FireStation fireStation);

  /**
   * Updates an existing fire station.
   *
   * @param fireStation the fire station with updated data
   * @return the updated fire station
   */
  FireStation updateFireStation(FireStation fireStation);

  /**
   * Deletes a fire station.
   *
   * @param fireStation the fire station to delete
   */
  void deleteFireStation(FireStation fireStation);
}