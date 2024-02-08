package com.example.closestgasstations.repository;

import com.example.closestgasstations.model.GasStation;
import com.example.closestgasstations.model.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    List<GasStation> findAllByZipCode(ZipCode zipCode);
}