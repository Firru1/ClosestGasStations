package com.example.closestgasstations.exceptions;

public class GasStationNotFoundException extends RuntimeException {
    public GasStationNotFoundException(String message) {
        super(message);
    }
}
