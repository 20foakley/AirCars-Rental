package com.example.carrentalproject.service;

import com.example.carrentalproject.model.Vehicle;
import com.example.carrentalproject.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehiclesRepository;

    public List<Vehicle> getAllVehicles() {
        return vehiclesRepository.findAll();
    }
}