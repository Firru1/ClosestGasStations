package com.example.closestgasstations.service;

import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.GasStationRepository;
import com.example.closestgasstations.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GasStationService {

    @Autowired
    private GasStationRepository gasStationRepository;

    @Autowired
    private ZipCodeRepository zipCodeRepository;

    public List<GasStation> getAllGasStations() {
        return gasStationRepository.findAll();
    }

    public List<GasStation> findNearestGasStations(String zipCodeValue) {
        Optional<ZipCode> zipCode = zipCodeRepository.findByZipCode(zipCodeValue);
        if (zipCode.isEmpty()) {
            throw new RuntimeException("Zip code not found.");
        }
        ZipCode zc = zipCode.get();
        return gasStationRepository.findAll().stream()
                .filter(station -> DistanceCalculator.calculateDistance(zc.getLatitude().doubleValue(), zc.getLongitude().doubleValue(), station.getLatitude().doubleValue(), station.getLongitude().doubleValue()) <= 2)
                .collect(Collectors.toList());
    }

    public GasStation addGasStation(GasStation gasStation) {
        if (gasStation.getZipCode() != null && gasStation.getZipCode().getId() != null) {
            Optional<ZipCode> zipCodeOptional = zipCodeRepository.findById(gasStation.getZipCode().getId());
            if (zipCodeOptional.isEmpty()) {
                throw new RuntimeException("Zip code not found with id: " + gasStation.getZipCode().getId());
            }
            gasStation.setZipCode(zipCodeOptional.get());
        } else {
            throw new RuntimeException("Zip code information is required");
        }

        return gasStationRepository.save(gasStation);
    }

    public GasStation updateGasStation(Long id, GasStation updatedGasStation) {
        return gasStationRepository.findById(id)
                .map(gasStation -> {
                    gasStation.setName(updatedGasStation.getName());
                    gasStation.setAddress(updatedGasStation.getAddress());
                    gasStation.setLatitude(updatedGasStation.getLatitude());
                    gasStation.setLongitude(updatedGasStation.getLongitude());
                    gasStation.setZipCode(updatedGasStation.getZipCode());
                    return gasStationRepository.save(gasStation);
                })
                .orElseThrow(() -> new RuntimeException("Gas station not found with id " + id));
    }

    public void deleteGasStation(Long id) {
        gasStationRepository.deleteById(id);
    }
}
