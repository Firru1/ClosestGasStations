package com.example.closestgasstations.controller;

import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.service.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zip-codes")
public class ZipCodeController {

    @Autowired
    private ZipCodeService zipCodeService;

    @PostMapping
    public ResponseEntity<ZipCode> addZipCode(@RequestBody ZipCode zipCode) {
        ZipCode newZipCode = zipCodeService.addZipCode(zipCode);
        return ResponseEntity.ok(newZipCode);
    }
}
