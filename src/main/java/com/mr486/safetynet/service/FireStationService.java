package com.mr486.safetynet.service;

import com.mr486.safetynet.expetion.EntityAlreadyExistsException;
import com.mr486.safetynet.expetion.EntityNotFoundException;
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

  public void saveFireStation(FireStation fireStation) {
    if(fireStationRepository.existsByAddress(fireStation.getAddress())) {
      String message = "fire station with address [" + fireStation.getAddress() + "] already exists !";
      throw new EntityAlreadyExistsException(message);
    }
    fireStationRepository.saveFireStation(fireStation);
  }

  public void updateFireStation(FireStation fireStation) {
    if(!fireStationRepository.existsByAddress(fireStation.getAddress())) {
      String message = "fire station with address [" + fireStation.getAddress() + "] does not exist !";
      throw new EntityNotFoundException(message);
    }
    fireStationRepository.updateFireStation(fireStation);
  }

  public void deleteFireStation(FireStation fireStation) {
    if(!fireStationRepository.existsByAddress(fireStation.getAddress())) {
      String message = "fire station with address [" + fireStation.getAddress() + "] does not exist !";
      throw new EntityNotFoundException(message);
    }
    fireStationRepository.deletefireStation(fireStation);
  }
}
