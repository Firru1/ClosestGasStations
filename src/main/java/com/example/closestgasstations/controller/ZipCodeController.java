package com.example.closestgasstations.controller;

import com.example.closestgasstations.dto.ZipCodeDTO;
import com.example.closestgasstations.service.ZipCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/zip-codes")
public class ZipCodeController {

    private final ZipCodeService zipCodeService;

    public ZipCodeController(ZipCodeService zipCodeService) {
        this.zipCodeService = zipCodeService;
    }

    @PostMapping
    public ResponseEntity<ZipCodeDTO> addZipCode(@Valid @RequestBody ZipCodeDTO zipCodeDTO) {
        ZipCodeDTO newZipCode = zipCodeService.addZipCode(zipCodeDTO);
        return ResponseEntity.ok(newZipCode);
    }
}
