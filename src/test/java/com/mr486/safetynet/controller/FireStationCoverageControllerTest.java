package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.FireStationCoverageDto;
import com.mr486.safetynet.dto.PersonInfoDto;
import com.mr486.safetynet.service.FireStationCoverageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the FireStationCoverageController.
 * Tests the endpoint for retrieving fire station coverage information.
 */
class FireStationCoverageControllerTest {

  private FireStationCoverageService fireStationCoverageService;
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    fireStationCoverageService = Mockito.mock(FireStationCoverageService.class);
    FireStationCoverageController controller = new FireStationCoverageController(fireStationCoverageService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  /**
   * Tests the getCoverageByStation endpoint with a valid station number.
   * Expects a 200 OK response with the correct body.
   */
  @Test
  void getCoverageByStation_shouldReturnOkWithBody() throws Exception {
    // GIVEN
    PersonInfoDto person = new PersonInfoDto("John", "Doe", "123 Main St", "111-111-1111");
    FireStationCoverageDto responseDto = new FireStationCoverageDto(List.of(person), 1, 0);

    when(fireStationCoverageService.getCoverageByStationNumber(1)).thenReturn(responseDto);

    // WHEN / THEN
    mockMvc.perform(get("/firestation")
                    .param("stationNumber", "1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.persons[0].firstName").value("John"))
            .andExpect(jsonPath("$.adultCount").value(1))
            .andExpect(jsonPath("$.childCount").value(0));
  }

  /**
   * Tests the getCoverageByStation endpoint with an invalid station number.
   * Expects a 400 Bad Request response.
   */
  @Test
  void getCoverageByStation_shouldReturnNoContentWhenEmpty() throws Exception {
    // GIVEN
    FireStationCoverageDto emptyDto = new FireStationCoverageDto(List.of(), 0, 0);
    when(fireStationCoverageService.getCoverageByStationNumber(99)).thenReturn(emptyDto);

    // WHEN / THEN
    mockMvc.perform(get("/firestation")
                    .param("stationNumber", "99")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
  }
}
