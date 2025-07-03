package com.mr486.safetynet.domain.domain_firestation.service;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;

import java.util.List;

public interface FireStationService {
  List<FireStation> getAllFireStations();

  List<FireStation> getFireStationsByStationNumber(Integer stationNumber);

  Boolean exists(FireStation FireStation);

  Boolean existsByAddress(String address);

  FireStation getFireStationByAddress(String address);

  FireStation addFireStation(FireStation fireStation);

  FireStation updateFireStation(FireStation fireStation);

  void deleteFireStation(FireStation fireStation);
}
