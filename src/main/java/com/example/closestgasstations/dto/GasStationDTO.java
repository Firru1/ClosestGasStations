package com.example.closestgasstations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GasStationDTO {
    private Long id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String zipCode; // Using zipCode string directly instead of ZipCode entity

}
