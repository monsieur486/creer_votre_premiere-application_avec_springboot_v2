package com.mr486.safetynet.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JsonDataLoader class.
 * This class verifies the behavior of the JsonDataLoader when loading JSON data.
 */
public class JsonDataLoaderTest {

  private JsonDataLoader jsonDataLoader;

  /**
   * Sets up the test environment before each test.
   * Initializes the JsonDataLoader instance.
   */
  @BeforeEach
  void setUp() {
    jsonDataLoader = new JsonDataLoader();
  }

  /**
   * Tests that loadData() returns a valid DataBinding object when the JSON file exists and is valid.
   *
   * @throws IOException if an error occurs while creating the temporary file.
   */
  @Test
  void loadData_shouldReturnDataBinding_whenFileExistsAndValid() throws IOException {
    // Préparation d’un fichier JSON temporaire valide
    File tempFile = File.createTempFile("test", ".json");
    tempFile.deleteOnExit();
    String jsonContent = "{\"firestations\":[]}";
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(jsonContent);
    }

    DataBinding dataBinding = jsonDataLoader.loadData(tempFile.getAbsolutePath());
    assertNotNull(dataBinding);
    assertNotNull(dataBinding.getFirestations());
  }

  /**
   * Tests that loadData() throws a RuntimeException when the file does not exist.
   */
  @Test
  void loadData_shouldThrowException_whenFileDoesNotExist() {
    String fakePath = "non_existent_file.json";
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      jsonDataLoader.loadData(fakePath);
    });
    assertTrue(exception.getMessage().contains("File not found"));
  }

  /**
   * Tests that loadData() throws a RuntimeException when the file contains invalid JSON.
   *
   * @throws IOException if an error occurs while creating the temporary file.
   */
  @Test
  void loadData_shouldThrowException_whenFileIsInvalidJson() throws IOException {
    // Fichier temporaire avec contenu invalide
    File tempFile = File.createTempFile("invalid", ".json");
    tempFile.deleteOnExit();
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("not a json");
    }

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      jsonDataLoader.loadData(tempFile.getAbsolutePath());
    });
    assertTrue(exception.getMessage().contains("Error while reading the JSON file"));
  }
}