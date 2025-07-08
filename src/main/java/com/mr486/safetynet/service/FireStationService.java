package com.mr486.safetynet.service;

import com.mr486.safetynet.exeption.EntityAlreadyExistsException;
import com.mr486.safetynet.exeption.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing FireStation entities.
 * Provides methods for CRUD operations and business logic related to fire stations.
 */
@Service
@RequiredArgsConstructor
public class FireStationService {

  private final FireStationRepository fireStationRepository;

  /**
   * Retrieves a fire station by its address.
   *
   * @param address the address of the fire station to retrieve.
   * @return an Optional containing the FireStation if found, or empty otherwise.
   */
  public Optional<FireStation> getFireStationByAdress(String address) {
    return fireStationRepository.getFireStationByAddress(address);
  }

  /**
   * Saves a new fire station.
   * Throws an exception if a fire station with the same address already exists.
   *
   * @param fireStation the FireStation entity to save.
   * @throws EntityAlreadyExistsException if a fire station with the given address already exists.
   */
  public void saveFireStation(FireStation fireStation) {
    if (fireStationRepository.existsByAddress(fireStation.getAddress())) {
      String message = "fire station with address [" + fireStation.getAddress() + "] already exists !";
      throw new EntityAlreadyExistsException(message);
    }
    fireStationRepository.saveFireStation(fireStation);
  }

  /**
   * Updates an existing fire station.
   * Throws an exception if the fire station does not exist.
   *
   * @param fireStation the FireStation entity with updated information.
   * @throws EntityNotFoundException if the fire station with the given address does not exist.
   */
  public void updateFireStation(FireStation fireStation) {
    if (!fireStationRepository.existsByAddress(fireStation.getAddress())) {
      String message = "fire station with address [" + fireStation.getAddress() + "] does not exist !";
      throw new EntityNotFoundException(message);
    }
    fireStationRepository.updateFireStation(fireStation);
  }

  /**
   * Deletes a fire station by its address.
   * Throws an exception if the fire station does not exist.
   *
   * @param address the address of the fire station to delete.
   * @throws EntityNotFoundException if the fire station with the given address does not exist.
   */
  public void deleteFireStationByAddress(String address) {
    if (!fireStationRepository.existsByAddress(address)) {
      String message = "fire station with address [" + address + "] does not exist !";
      throw new EntityNotFoundException(message);
    }
    fireStationRepository.deleteFireStationByAddress(address);
  }

  /**
   * Retrieves all fire stations associated with a specific station number.
   *
   * @param stationNumber the station number to filter fire stations.
   * @return a list of FireStation entities associated with the given station number.
   */
  public List<FireStation> getAllFireStationsByStationNumer(Integer stationNumber) {
    return fireStationRepository.getAllFireStationsByStationNumber(stationNumber);
  }
}
