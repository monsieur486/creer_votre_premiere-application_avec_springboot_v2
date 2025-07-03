package com.mr486.safetynet.domain.domain_firestation.repository;

import com.mr486.safetynet.domain.domain_firestation.model.Firestation;

import java.util.List;

/**
 * Interface for managing fire station data.
 * Provides methods for CRUD operations and querying fire stations.
 */
public interface FirestationRepository {

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations.
   */
  List<Firestation> getAllFirestations();

  /**
   * Retrieves fire stations by their station number.
   *
   * @param stationNumber the station number to filter by.
   * @return a list of fire stations matching the given station number.
   */
  List<Firestation> getAllFirestationsByStationNumber(Integer stationNumber);

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return the fire station matching the given address, or null if not found.
   */
  Firestation getFirestationByAddress(String address);

  /**
   * Adds a new fire station.
   *
   * @param firestation the fire station to add.
   * @return the added fire station.
   */
  Firestation addFirestation(Firestation firestation);

  /**
   * Updates an existing fire station.
   *
   * @param firestation the fire station with updated information.
   * @return the updated fire station, or null if the fire station does not exist.
   */
  Firestation updateFirestation(Firestation firestation);

  /**
   * Deletes a fire station.
   *
   * @param firestation the fire station to delete.
   */
  void deleteFirestation(Firestation firestation);

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check.
   * @return true if a fire station exists at the given address, false otherwise.
   */
  Boolean existByAddress(String address);
}