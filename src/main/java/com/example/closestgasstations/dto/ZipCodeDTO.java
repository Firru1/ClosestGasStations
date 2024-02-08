package com.example.closestgasstations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZipCodeDTO {
    private Long id;
    private String zipCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
