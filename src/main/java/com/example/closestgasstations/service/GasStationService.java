package com.example.closestgasstations.service;

import com.example.closestgasstations.dto.GasStationDTO;
import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.GasStationRepository;
import com.example.closestgasstations.repository.ZipCodeRepository;
import com.example.closestgasstations.util.DistanceCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GasStationService {

    private final GasStationRepository gasStationRepository;
    private final ZipCodeRepository zipCodeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public GasStationService(GasStationRepository gasStationRepository, ZipCodeRepository zipCodeRepository) {
        this.gasStationRepository = gasStationRepository;
        this.zipCodeRepository = zipCodeRepository;
    }

    public List<GasStationDTO> getAllGasStations() {
        return gasStationRepository.findAll().stream()
                .map(gasStation -> modelMapper.map(gasStation, GasStationDTO.class))
                .collect(Collectors.toList());
    }

    public List<GasStationDTO> findNearestGasStations(String zipCodeValue) {
        ZipCode zipCode = zipCodeRepository.findByZipCode(zipCodeValue)
                .orElseThrow(() -> new RuntimeException("Zip code not found."));
        return gasStationRepository.findAll().stream()
                .filter(station -> DistanceCalculator.calculateDistance(zipCode.getLatitude().doubleValue(), zipCode.getLongitude().doubleValue(), station.getLatitude().doubleValue(), station.getLongitude().doubleValue()) <= 2)
                .map(station -> modelMapper.map(station, GasStationDTO.class))
                .collect(Collectors.toList());
    }

   public GasStationDTO addGasStation(GasStationDTO gasStationDTO) {
        // Check if the zip code exists
        ZipCode zipCode = zipCodeRepository.findByZipCode(gasStationDTO.getZipCode())
                .orElseGet(() -> {
                    // If it doesn't exist, create a new ZipCode entity
                    // This assumes ZipCodeDTO or similar payload contains necessary info
                    ZipCode newZipCode = new ZipCode();
                    newZipCode.setZipCode(gasStationDTO.getZipCode());
                    // Set additional properties if needed
                    return zipCodeRepository.save(newZipCode);
                });

        // Now, proceed to add the Gas Station linked to the ZipCode entity
       GasStation gasStation = new GasStation();
       gasStation.setName(gasStationDTO.getName());
       gasStation.setAddress(gasStationDTO.getAddress());
       gasStation.setLatitude(gasStationDTO.getLatitude());
       gasStation.setLongitude(gasStationDTO.getLongitude());
       gasStation.setZipCode(zipCode); // Link to the ZipCode entity

       GasStation savedGasStation = gasStationRepository.save(gasStation);
       return modelMapper.map(savedGasStation, GasStationDTO.class);
    }

    public GasStationDTO updateGasStation(Long id, GasStationDTO gasStationDTO) {
        GasStation gasStation = gasStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gas station not found with id " + id));

        modelMapper.map(gasStationDTO, gasStation); // Directly map DTO to the existing entity
        gasStation.setZipCode(zipCodeRepository.findByZipCode(gasStationDTO.getZipCode())
                .orElseThrow(() -> new RuntimeException("Zip code not found.")));

        GasStation updatedGasStation = gasStationRepository.save(gasStation);
        return modelMapper.map(updatedGasStation, GasStationDTO.class);
    }

    public void deleteGasStationByZipCode(String zipCodeValue) {
        ZipCode zipCode = zipCodeRepository.findByZipCode(zipCodeValue)
                .orElseThrow(() -> new RuntimeException("Zip code not found."));
        List<GasStation> gasStations = gasStationRepository.findAllByZipCode(zipCode);
        gasStations.forEach(gasStation -> gasStationRepository.deleteById(gasStation.getId()));
    }
}
