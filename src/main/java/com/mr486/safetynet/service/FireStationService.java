package com.mr486.safetynet.service;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FireStationService {

  private final FireStationRepository fireStationRepository;

  public Optional<FireStation> getFireStation(String address) {
    return fireStationRepository.getFireStationByAddress(address);
  }

  public List<FireStation> getAllFireStations() {
    return fireStationRepository.getAllFireStations();
  }
}
