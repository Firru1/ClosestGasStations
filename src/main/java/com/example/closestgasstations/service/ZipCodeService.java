package com.example.closestgasstations.service;

import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.ZipCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    private final ZipCodeRepository zipCodeRepository;

    public ZipCodeService(ZipCodeRepository zipCodeRepository) {
        this.zipCodeRepository = zipCodeRepository;
    }

    public ZipCode addZipCode(ZipCode zipCode) {
        return zipCodeRepository.save(zipCode);
    }
}
