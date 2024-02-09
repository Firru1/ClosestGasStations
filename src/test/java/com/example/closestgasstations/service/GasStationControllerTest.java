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

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


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

    // Add more tests for other endpoints...
}
