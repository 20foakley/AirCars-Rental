package com.example.carrentalproject.repository;

import com.example.carrentalproject.model.Vehicles;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VehiclesRepository {
    public List<Vehicles> findAll() {
        return new ArrayList<>();
    }
}
