package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonDataLoader {

  public DataBinding loadData(String filePath) {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(filePath);

    if (!file.exists()) {
      throw new RuntimeException("File not found : " + filePath);
    }

    try {
      return mapper.readValue(file, DataBinding.class);
    } catch (IOException e) {
      throw new RuntimeException("Error while reading the JSON file: " + e.getMessage(), e);
    }
  }
}

