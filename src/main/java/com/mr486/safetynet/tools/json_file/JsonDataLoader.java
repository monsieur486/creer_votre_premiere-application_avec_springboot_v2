package com.mr486.safetynet.tools.json_file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Component responsible for loading data from a JSON file.
 * Utilizes Jackson's ObjectMapper to deserialize JSON into a DataBinding object.
 */
@Component
@Slf4j
public class JsonDataLoader {

  @Value("${json.file.path}")
  String dataFilePath;

  /**
   * Loads data from a specified JSON file path and maps it to a DataBinding object.
   *
   * @return a DataBinding object containing the deserialized data
   * @throws RuntimeException if the file is not found or an error occurs while reading the file
   */
  public DataBinding loadData() {
    ObjectMapper mapper = new ObjectMapper();

    DataBinding dataBinding = new DataBinding();

    try {
      File file = new File(dataFilePath);
      return mapper.readValue(file, DataBinding.class);
    } catch (Exception e) {
      throw new RuntimeException("Error reading json file:" +dataFilePath + " message: "+ e.getMessage());
    }
  }
}