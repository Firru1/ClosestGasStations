package com.example.closestgasstations.controller;

import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.service.GasStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gas-stations")
public class GasStationController {

    private final GasStationService gasStationService;

    public GasStationController(GasStationService gasStationService) {
        this.gasStationService = gasStationService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<List<GasStation>> getNearestGasStations(@RequestParam String zipCode) {
        List<GasStation> gasStations = gasStationService.findNearestGasStations(zipCode);
        return ResponseEntity.ok(gasStations);
    }

    @PostMapping
    public ResponseEntity<GasStation> addGasStation(@RequestBody GasStation gasStation) {
        GasStation newGasStation = gasStationService.addGasStation(gasStation);
        return ResponseEntity.ok(newGasStation);
    }

    @GetMapping
    public ResponseEntity<List<GasStation>> getAllGasStations() {
        List<GasStation> gasStations = gasStationService.getAllGasStations();
        return ResponseEntity.ok(gasStations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GasStation> updateGasStation(@PathVariable Long id, @RequestBody GasStation gasStation) {
        GasStation updatedGasStation = gasStationService.updateGasStation(id, gasStation);
        return ResponseEntity.ok(updatedGasStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGasStation(@PathVariable Long id) {
        gasStationService.deleteGasStation(id);
        return ResponseEntity.ok().build();
    }
}
