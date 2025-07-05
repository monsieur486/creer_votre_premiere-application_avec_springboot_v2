package com.mr486.safetynet.tools.json_file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Component responsible for loading data from a JSON file.
 * Utilizes Jackson's ObjectMapper to deserialize JSON into a DataBinding object.
 */
@Component
@Slf4j
public class JsonDataLoader {

  /**
   * Loads data from a specified JSON file path and maps it to a DataBinding object.
   *
   * @param filePath the path to the JSON file to be loaded
   * @return a DataBinding object containing the deserialized data
   * @throws RuntimeException if the file is not found or an error occurs while reading the file
   */
  public DataBinding loadData(String filePath) {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(filePath);

    // Check if the file exists, log an error, and throw an exception if it does not
    if (!file.exists()) {
      log.error("File not found: {}", filePath);
      throw new RuntimeException("File not found : " + filePath);
    }

    try {
      // Attempt to read and map the JSON file to a DataBinding object
      return mapper.readValue(file, DataBinding.class);
    } catch (IOException e) {
      // Log the error and throw a runtime exception if an I/O error occurs
      log.error("Error reading JSON file: {}", e.getMessage());
      throw new RuntimeException("Error while reading the JSON file: " + e.getMessage(), e);
    }
  }
}