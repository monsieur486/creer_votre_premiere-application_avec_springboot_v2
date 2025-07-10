package com.mr486.safetynet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FireStationCoverageDto {
  private List<PersonInfo> persons;
  private long adultCount;
  private long childCount;

  @Data
  @AllArgsConstructor
  public static class PersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }
}
