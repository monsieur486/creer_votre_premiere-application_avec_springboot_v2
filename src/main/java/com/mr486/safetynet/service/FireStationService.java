package com.mr486.safetynet.service;

import com.mr486.safetynet.expetion.EntityAlreadyExistsException;
import com.mr486.safetynet.expetion.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the FireStationService interface.
 * Provides methods for managing fire stations, including CRUD operations and validations.
 */
@Service
public class FireStationService {

  private final FireStationRepository fireStationRepository;

  /**
   * Constructor for FireStationService.
   *
   * @param fireStationRepository the repository used for fire station data access
   */
  public FireStationService(FireStationRepository fireStationRepository) {
    this.fireStationRepository = fireStationRepository;
  }

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations
   */
  public List<FireStation> getAllFireStations() {
    return fireStationRepository.getAllFireStations();
  }

  /**
   * Retrieves fire stations by their station number.
   * If the station number is null, returns all fire stations.
   *
   * @param stationNumber the station number to filter by
   * @return a list of fire stations with the specified station number
   */
  public List<FireStation> getFireStationsByStationNumber(Integer stationNumber) {
    if (stationNumber == null) {
      return fireStationRepository.getAllFireStations();
    }
    return fireStationRepository.getAllFireStationsByStationNumber(stationNumber);
  }

  /**
   * Checks if a specific fire station exists.
   *
   * @param fireStation the fire station to check
   * @return true if the fire station exists, false otherwise
   */
  public Boolean exists(FireStation fireStation) {
    if (fireStation == null || fireStation.getAddress() == null || fireStation.getStation() == null) {
      return false;
    }

    FireStation existingFirestation = fireStationRepository.getFireStationByAddress(fireStation.getAddress());
    return existingFirestation != null && existingFirestation.getStation().equals(fireStation.getStation());
  }

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check
   * @return true if a fire station exists at the specified address, false otherwise
   */
  public Boolean existsByAddress(String address) {
    if (address == null || address.isEmpty()) {
      return false;
    }

    FireStation existingFirestation = fireStationRepository.getFireStationByAddress(address);
    return existingFirestation != null;
  }

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station
   * @return the fire station at the specified address, or null if not found
   */
  public FireStation getFireStationByAddress(String address) {
    if (address == null || address.isEmpty()) {
      return null;
    }

    return fireStationRepository.getFireStationByAddress(address);
  }

  /**
   * Adds a new fire station.
   * Throws an exception if a fire station with the same address already exists.
   *
   * @param fireStation the fire station to add
   * @return the added fire station
   * @throws IllegalArgumentException if a fire station with the same address already exists
   */
  public FireStation addFireStation(FireStation fireStation) {
    if (existsByAddress(fireStation.getAddress())) {
      throw new EntityAlreadyExistsException(
          "Fire station with address "
                  + fireStation.getAddress()
                  + " already exists."
      );
    }

    fireStationRepository.addfireStation(fireStation);

    return fireStation;
  }

  /**
   * Updates an existing fire station.
   *
   * @param fireStation the fire station with updated data
   * @return the updated fire station
   */
  public FireStation updateFireStation(FireStation fireStation) {
    if(!exists(fireStation)) {
      throw  new EntityNotFoundException(
              "Fire station with address "
                      + fireStation.getAddress()
                      + " does not exist.");

    }
    return fireStationRepository.getFireStationByAddress(fireStation.getAddress());
  }

  /**
   * Deletes a fire station.
   * Throws an exception if the fire station or its address is null, or if it does not exist.
   *
   * @param fireStation the fire station to delete
   * @throws IllegalArgumentException if the fire station or its address is null, or if it does not exist
   */
  public void deleteFireStation(FireStation fireStation) {
    if(!exists(fireStation)) {
      throw  new EntityNotFoundException(
              "Fire station with address "
                      + fireStation.getAddress()
                      + " does not exist.");

    }

    fireStationRepository.deletefireStation(fireStation);
  }
}