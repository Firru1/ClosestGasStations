package com.example.closestgasstations.service;

import com.example.closestgasstations.dto.GasStationDTO;
import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.GasStationRepository;
import com.example.closestgasstations.repository.ZipCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GasStationServiceTest {

    @Mock
    private GasStationRepository gasStationRepository;

    @Mock
    private ZipCodeRepository zipCodeRepository;

    @InjectMocks
    private GasStationService gasStationService;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        gasStationService = new GasStationService(gasStationRepository, zipCodeRepository, modelMapper);
    }

    @Test
    void testAddGasStation() {
        ZipCode zipCode = new ZipCode();
        zipCode.setId(1L);
        zipCode.setZipCode("12345");
        zipCode.setLatitude(new BigDecimal("40.7128"));
        zipCode.setLongitude(new BigDecimal("-74.0060"));

        GasStationDTO gasStationDTO = new GasStationDTO();
        gasStationDTO.setName("Test Station");
        gasStationDTO.setAddress("123 Test Ave");
        gasStationDTO.setLatitude(new BigDecimal("40.7128"));
        gasStationDTO.setLongitude(new BigDecimal("-74.0060"));
        gasStationDTO.setZipCode("12345");

        GasStation gasStation = modelMapper.map(gasStationDTO, GasStation.class);
        gasStation.setId(1L);

        when(zipCodeRepository.findByZipCode(any(String.class))).thenReturn(Optional.of(zipCode));
        when(gasStationRepository.save(any(GasStation.class))).thenReturn(gasStation);

        GasStationDTO result = gasStationService.addGasStation(gasStationDTO);

        assertEquals(gasStationDTO.getName(), result.getName());
        assertEquals(gasStationDTO.getAddress(), result.getAddress());
        assertEquals(gasStationDTO.getZipCode(), result.getZipCode());
    }


    //Need to more test cases

}
