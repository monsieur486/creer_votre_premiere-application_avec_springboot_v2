package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class JsonDataLoader {

  public DataBinding loadData(String filePath) {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(filePath);

    if (!file.exists()) {
      log.error("File not found: {}", filePath);
      throw new RuntimeException("File not found : " + filePath);
    }

    try {
      return mapper.readValue(file, DataBinding.class);
    } catch (IOException e) {
      log.error("Error reading JSON file: {}", e.getMessage());
      throw new RuntimeException("Error while reading the JSON file: " + e.getMessage(), e);
    }
  }
}

