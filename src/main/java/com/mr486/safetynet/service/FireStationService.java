package com.mr486.safetynet.service;

import com.mr486.safetynet.expetion.EntityAlreadyExistsException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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

  public void saveFireStation(FireStation fireStation) {
    if(fireStationRepository.existsByAddress(fireStation.getAddress())) {
      throw new EntityAlreadyExistsException("Firestation already exist");
    }
    fireStationRepository.saveFireStation(fireStation);
  }
}
