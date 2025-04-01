package com.example.carrentalproject.service;

import com.example.carrentalproject.model.Vehicles;
import com.example.carrentalproject.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiclesService {
    @Autowired
    private VehiclesRepository vehiclesRepository;

    public List<Vehicles> getAllVehicles() {
        return vehiclesRepository.findAll();
    }
}