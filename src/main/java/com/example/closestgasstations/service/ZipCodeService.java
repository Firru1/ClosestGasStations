package com.example.closestgasstations.service;

import com.example.closestgasstations.dto.ZipCodeDTO;
import com.example.closestgasstations.model.ZipCode;
import com.example.closestgasstations.repository.ZipCodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    private final ZipCodeRepository zipCodeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ZipCodeService(ZipCodeRepository zipCodeRepository) {
        this.zipCodeRepository = zipCodeRepository;
    }

    public ZipCodeDTO addZipCode(ZipCodeDTO zipCodeDTO) {
        ZipCode existingZipCode = zipCodeRepository.findByZipCode(zipCodeDTO.getZipCode()).orElse(null);
        if (existingZipCode != null) {
            return modelMapper.map(existingZipCode, ZipCodeDTO.class);
        }
        ZipCode zipCode = modelMapper.map(zipCodeDTO, ZipCode.class);
        ZipCode savedZipCode = zipCodeRepository.save(zipCode);
        return modelMapper.map(savedZipCode, ZipCodeDTO.class);
    }
}
