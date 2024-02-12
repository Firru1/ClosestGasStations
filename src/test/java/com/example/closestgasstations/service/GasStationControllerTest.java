package com.example.closestgasstations.service;

import com.example.closestgasstations.controller.GasStationController;
import com.example.closestgasstations.dto.GasStationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(MockitoExtension.class)
public class GasStationControllerTest {

    @Mock
    private GasStationService gasStationService;

    @InjectMocks
    private GasStationController gasStationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gasStationController).build();
    }

    @Test
    public void testGetAllGasStations() throws Exception {
        GasStationDTO gasStationDTO = new GasStationDTO();
        gasStationDTO.setId(1L);
        gasStationDTO.setName("Test Station");

        given(gasStationService.getAllGasStations()).willReturn(Collections.singletonList(gasStationDTO));

        mockMvc.perform(get("/api/gas-stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Test Station'}]"));
    }

    @Test
    public void testDeleteGasStation() throws Exception {
        String zipCode = "12345";

        mockMvc.perform(delete("/api/gas-stations/" + zipCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gasStationService, times(1)).deleteGasStationsByZipCode(zipCode);
    }

    @Test
    public void testGetNearestGasStations() throws Exception {
        String zipCode = "10001";
        List<GasStationDTO> nearestStations = Arrays.asList(
                new GasStationDTO(1L, "Station 1", "123 Main St", new BigDecimal("40.7128"), new BigDecimal("-74.0060"), zipCode),
                new GasStationDTO(2L, "Station 2", "456 Elm St", new BigDecimal("40.7138"), new BigDecimal("-74.0059"), zipCode)
        );

        given(gasStationService.findNearestGasStations(zipCode)).willReturn(nearestStations);

        mockMvc.perform(get("/api/gas-stations/nearest?zipCode=" + zipCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Expect 2 stations in the response
                .andExpect(jsonPath("$[0].name", is("Station 1")))
                .andExpect(jsonPath("$[1].name", is("Station 2")));
    }

    //Need to add test cases

}