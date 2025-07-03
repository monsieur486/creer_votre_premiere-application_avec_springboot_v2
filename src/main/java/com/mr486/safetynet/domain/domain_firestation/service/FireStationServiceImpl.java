package com.mr486.safetynet.domain.domain_firestation.service;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;
import com.mr486.safetynet.domain.domain_firestation.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the FireStationService interface.
 * Provides methods for managing fire stations, including CRUD operations and validations.
 */
@Service
public class FireStationServiceImpl implements FireStationService {

  private final FireStationRepository fireStationRepository;

  /**
   * Constructor for FireStationServiceImpl.
   *
   * @param fireStationRepository the repository used for fire station data access
   */
  public FireStationServiceImpl(FireStationRepository fireStationRepository) {
    this.fireStationRepository = fireStationRepository;
  }

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations
   */
  @Override
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
  @Override
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
  @Override
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
  @Override
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
  @Override
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
  @Override
  public FireStation addFireStation(FireStation fireStation) {
    if (existsByAddress(fireStation.getAddress())) {
      throw new IllegalArgumentException("Firestation with this address already exists");
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
  @Override
  public FireStation updateFireStation(FireStation fireStation) {
    return fireStationRepository.getFireStationByAddress(fireStation.getAddress());
  }

  /**
   * Deletes a fire station.
   * Throws an exception if the fire station or its address is null, or if it does not exist.
   *
   * @param fireStation the fire station to delete
   * @throws IllegalArgumentException if the fire station or its address is null, or if it does not exist
   */
  @Override
  public void deleteFireStation(FireStation fireStation) {
    if (fireStation == null || fireStation.getAddress() == null) {
      throw new IllegalArgumentException("Firestation or address cannot be null");
    }

    FireStation existingFirestation = fireStationRepository.getFireStationByAddress(fireStation.getAddress());
    if (existingFirestation == null) {
      throw new IllegalArgumentException("Firestation with this address does not exist");
    }

    fireStationRepository.deletefireStation(existingFirestation);
  }
}