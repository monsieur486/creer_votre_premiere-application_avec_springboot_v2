package com.mr486.safetynet.domain.domain_firestation.service;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;
import com.mr486.safetynet.domain.domain_firestation.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

  private final FireStationRepository fireStationRepository;

  public FireStationServiceImpl(FireStationRepository fireStationRepository) {
    this.fireStationRepository = fireStationRepository;
  }

  @Override
  public List<FireStation> getAllFireStations() {
    return fireStationRepository.getAllFireStations();
  }

  @Override
  public List<FireStation> getFireStationsByStationNumber(Integer stationNumber) {
    if (stationNumber == null) {
      return fireStationRepository.getAllFireStations();
    }
    return fireStationRepository.getAllFireStationsByStationNumber(stationNumber);
  }

  @Override
  public Boolean exists(FireStation fireStation) {
    if (fireStation == null || fireStation.getAddress() == null || fireStation.getStation() == null) {
      return false;
    }

    FireStation existingFirestation = fireStationRepository.getFireStationByAddress(fireStation.getAddress());
    return existingFirestation != null && existingFirestation.getStation().equals(fireStation.getStation());
  }

  @Override
  public Boolean existsByAddress(String address) {
    if (address == null || address.isEmpty()) {
      return false;
    }

    FireStation existingFirestation = fireStationRepository.getFireStationByAddress(address);
    return existingFirestation != null;
  }

  @Override
  public FireStation getFireStationByAddress(String address) {
    return null;
  }

  @Override
  public FireStation addFireStation(FireStation FireStation) {
    return null;
  }

  @Override
  public FireStation updateFireStation(FireStation FireStation) {
    return null;
  }

  @Override
  public void deleteFireStation(FireStation FireStation) {

  }
}
