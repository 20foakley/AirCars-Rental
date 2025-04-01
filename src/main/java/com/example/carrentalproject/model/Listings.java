package com.example.carrentalproject.model;


import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.Check;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="listings",schema="p2p_car_rental")
public class Listings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //------------------------------------//
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicles vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private Users owner;
    //------------------------------------//

    @Column(nullable = false, unique = true, length = 25)
    private String vin;

    @NotNull
    private float vehicle_value; // float = real

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String country;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    @NotNull
    private short duration;

    @NotNull
    private Date created_at;

    @NotNull
    private Date updated_at;

    @NotNull
    @Column(length=20)
    private String title;

    @NotNull
    @Column(length=20)
    private String description;



    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public float getVehicle_value() {
        return vehicle_value;
    }

    public void setVehicle_value(float vehicle_value) {
        this.vehicle_value = vehicle_value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
