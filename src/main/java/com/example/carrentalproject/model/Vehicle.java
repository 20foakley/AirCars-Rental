package com.example.carrentalproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "vehicles", schema = "p2p_car_rental")
@Check(constraints = "year >= 1900 AND year <= extract(year from current_date) + 1 AND (doors IS NULL OR doors > 0) AND (horsepower IS NULL OR horsepower > 0)")

public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false,unique = true)
    private String vin;

    @Column(length = 30, nullable = false)
    private String make;

    @Column(length = 30,nullable = false)
    private String model;

    @Column(length = 40)
    private String submodel;

    @Column(length=30)
    private String trim;

    @Column(length=30)
    private String color;

    @Column(nullable = false)
    private short year;

    @Column(name="city_mpg")
    private Short cityMpg;

    @Column(name="highway_mpg")
    private Short highwayMpg;

    @Column(name="combined_mpg")
    private Short combinedMpg;

    @Column(nullable = true)
    private Short doors;

    @Column(nullable = true)
    private Short horsepower;

    // Enum types
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "fuel_type")
    private FuelType fuelType;


    public Short getCityMpg() {
        return cityMpg;
    }

    public void setCityMpg(Short cityMpg) {
        this.cityMpg = cityMpg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSubmodel() {
        return submodel;
    }

    public void setSubmodel(String submodel) {
        this.submodel = submodel;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public Short getHighwayMpg() {
        return highwayMpg;
    }

    public void setHighwayMpg(Short highwayMpg) {
        this.highwayMpg = highwayMpg;
    }

    public Short getCombinedMpg() {
        return combinedMpg;
    }

    public void setCombinedMpg(Short combinedMpg) {
        this.combinedMpg = combinedMpg;
    }

    public Short getDoors() {
        return doors;
    }

    public void setDoors(Short doors) {
        this.doors = doors;
    }

    public Short getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Short horsepower) {
        this.horsepower = horsepower;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "transmission_type")
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "vehicle_type")
    private VehicleType vehicleType;


    public enum FuelType {
        REGULAR, PLUS,PREMIUM, DIESEL, ELECTRIC, HYBRID
    }

    public enum TransmissionType {
        MANUAL, AUTOMATIC
    }

    public enum VehicleType {
        SEDAN, CROSSOVER, SUV, PICKUP_TRUCK, HATCHBACK, MINIVAN, VAN, CONVERTIBLE, WAGON, SPORTS_CAR, RECREATIONAL_VEHICLE
    }


}

