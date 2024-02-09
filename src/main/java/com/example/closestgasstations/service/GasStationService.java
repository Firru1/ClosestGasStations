package com.example.closestgasstations.service;

import com.example.closestgasstations.dto.GasStationDTO;
import com.example.closestgasstations.exceptions.GasStationNotFoundException;
import com.example.closestgasstations.exceptions.ZipCodeNotFoundException;
import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.GasStationRepository;
import com.example.closestgasstations.repository.ZipCodeRepository;
import com.example.closestgasstations.util.DistanceCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GasStationService {

    private final GasStationRepository gasStationRepository;
    private final ZipCodeRepository zipCodeRepository;
    private final ModelMapper modelMapper;

    public GasStationService(GasStationRepository gasStationRepository, ZipCodeRepository zipCodeRepository, ModelMapper modelMapper) {
        this.gasStationRepository = gasStationRepository;
        this.zipCodeRepository = zipCodeRepository;
        this.modelMapper = modelMapper;
    }

    public List<GasStationDTO> getAllGasStations() {
        return gasStationRepository.findAll().stream()
                .map(gasStation -> modelMapper.map(gasStation, GasStationDTO.class))
                .collect(Collectors.toList());
    }

    public List<GasStationDTO> findNearestGasStations(String zipCodeValue) {
        ZipCode zipCode = zipCodeRepository.findByZipCode(zipCodeValue)
                .orElseThrow(() -> new ZipCodeNotFoundException("Zip code not found: " + zipCodeValue));
        return gasStationRepository.findAll().stream()
                .filter(station -> DistanceCalculator.calculateDistance(zipCode.getLatitude().doubleValue(), zipCode.getLongitude().doubleValue(), station.getLatitude().doubleValue(), station.getLongitude().doubleValue()) <= 2)
                .map(station -> modelMapper.map(station, GasStationDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public GasStationDTO addGasStation(GasStationDTO gasStationDTO) {
        ZipCode zipCode = zipCodeRepository.findByZipCode(gasStationDTO.getZipCode())
                .orElseThrow(() -> new ZipCodeNotFoundException("Zip code does not exist: " + gasStationDTO.getZipCode()));
        GasStation gasStation = modelMapper.map(gasStationDTO, GasStation.class);
        gasStation.setZipCode(zipCode);
        GasStation savedGasStation = gasStationRepository.save(gasStation);
        return modelMapper.map(savedGasStation, GasStationDTO.class);
    }

    @Transactional
    public GasStationDTO updateGasStation(Long id, GasStationDTO gasStationDTO) {
        GasStation gasStation = gasStationRepository.findById(id)
                .orElseThrow(() -> new GasStationNotFoundException("Gas station not found with id: " + id));

        gasStation.setName(gasStationDTO.getName());
        gasStation.setAddress(gasStationDTO.getAddress());
        gasStation.setLatitude(gasStationDTO.getLatitude());
        gasStation.setLongitude(gasStationDTO.getLongitude());
        ZipCode zipCode = zipCodeRepository.findByZipCode(gasStationDTO.getZipCode())
                .orElseThrow(() -> new ZipCodeNotFoundException("Zip code does not exist: " + gasStationDTO.getZipCode()));
        gasStation.setZipCode(zipCode);
        GasStation updatedGasStation = gasStationRepository.save(gasStation);
        return modelMapper.map(updatedGasStation, GasStationDTO.class);
    }


    @Transactional
    public void deleteGasStationsByZipCode(String zipCodeValue) {
        ZipCode zipCode = zipCodeRepository.findByZipCode(zipCodeValue)
                .orElseThrow(() -> new ZipCodeNotFoundException("Zip code not found: " + zipCodeValue));
        List<GasStation> gasStations = gasStationRepository.findAllByZipCode(zipCode);
        gasStations.forEach(gasStation -> gasStationRepository.deleteById(gasStation.getId()));
    }
}
