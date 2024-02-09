package com.example.closestgasstations.controller;

import com.example.closestgasstations.service.GasStationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.closestgasstations.dto.GasStationDTO;

import java.util.List;

@RestController
@RequestMapping("/api/gas-stations")
public class GasStationController {

    private final GasStationService gasStationService;

    public GasStationController(GasStationService gasStationService) {
        this.gasStationService = gasStationService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<List<GasStationDTO>> getNearestGasStations(@RequestParam String zipCode) {
        List<GasStationDTO> gasStations = gasStationService.findNearestGasStations(zipCode);
        return ResponseEntity.ok(gasStations);
    }

    @PostMapping
    public ResponseEntity<GasStationDTO> addGasStation(@Valid @RequestBody GasStationDTO gasStationDTO) {
        GasStationDTO newGasStation = gasStationService.addGasStation(gasStationDTO);
        return ResponseEntity.ok(newGasStation);
    }

    @GetMapping
    public ResponseEntity<List<GasStationDTO>> getAllGasStations() {
        List<GasStationDTO> gasStations = gasStationService.getAllGasStations();
        return ResponseEntity.ok(gasStations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GasStationDTO> updateGasStation(@PathVariable Long id, @Valid @RequestBody GasStationDTO gasStationDTO) {
        GasStationDTO updatedGasStation = gasStationService.updateGasStation(id, gasStationDTO);
        return ResponseEntity.ok(updatedGasStation);
    }

    @DeleteMapping("/{zipCode}")
    public ResponseEntity<?> deleteGasStation(@PathVariable String zipCode) {
        gasStationService.deleteGasStationsByZipCode(zipCode);
        return ResponseEntity.ok().build();
    }
}
