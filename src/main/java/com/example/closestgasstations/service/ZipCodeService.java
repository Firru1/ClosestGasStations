package com.example.closestgasstations.service;

import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    @Autowired
    private ZipCodeRepository zipCodeRepository;

    public ZipCode addZipCode(ZipCode zipCode) {
        return zipCodeRepository.save(zipCode);
    }
}
