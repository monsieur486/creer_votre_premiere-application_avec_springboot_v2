package com.mr486.safetynet.tools;

import com.mr486.safetynet.domain.domain_firestation.model.FireStation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class DataBinding {
  List<FireStation> firestations;
}