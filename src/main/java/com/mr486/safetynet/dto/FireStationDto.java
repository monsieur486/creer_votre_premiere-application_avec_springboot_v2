package com.mr486.safetynet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FireStationDto {
  @NotBlank(message = "Address cannot be blank")
  private String address;
}
