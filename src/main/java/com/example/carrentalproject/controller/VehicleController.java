package com.example.carrentalproject.controller;

import com.example.carrentalproject.model.Vehicles;
import com.example.carrentalproject.service.VehiclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehiclesService vehiclesService;

    @GetMapping
    public List<Vehicles> getAllVehicles() {
        return vehiclesService.getAllVehicles();
    }
}