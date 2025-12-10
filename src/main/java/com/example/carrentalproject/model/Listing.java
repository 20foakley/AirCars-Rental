package com.example.carrentalproject.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity
@Table(
        name = "listings",
        schema = "p2p_car_rental",
        uniqueConstraints = @UniqueConstraint(columnNames = "vehicle_id")
)

public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //------------------------------------//
    // Relationships
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id",  nullable = false)
    private Vehicle vehicle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id",  nullable = false)
    private User owner;
    //------------------------------------//

    @NotNull
    @Positive
    @DecimalMax(value = "200000", message="Cannot store a vehicle value more than 200000")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal vehicle_value; // float = real

    @NotNull
    @Column(length = 50,nullable = false)
    private String city;

    @NotNull
    @Column(length = 50, nullable = false)
    private String country;

    @NotNull
    @DecimalMin(value = "-90.0", message = "Latitude cannot be less than -90")
    @DecimalMax(value = "90.0", message = "Latitude cannot be greater than 90")
    @Column(precision = 8, scale = 6, nullable = false)
    private BigDecimal latitude;

    @NotNull
    @DecimalMin(value = "-180.0", message = "Longitude cannot be less than -180")
    @DecimalMax(value = "180.0", message = "Longitude cannot be greater than 180")
    @Column(precision = 9, scale = 6, nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Positive(message = "Max duration must be positive")
    @Max(value=90,message="Max duration of listing must be at most 90")
    @Column(name = "max_duration",nullable = false)
    private Short maxDuration;

    @NotNull
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @NotNull
    @Column(length=20,nullable = false)
    @Size(max = 20)
    private String title;

    @NotNull
    @Column(length=600,nullable = false)
    @Size(max = 600)
    private String description;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
    this.updatedAt = OffsetDateTime.now();
    }


    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getVehicle_value() {
        return vehicle_value;
    }

    public void setVehicle_value(BigDecimal vehicle_value) {
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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Short getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Short maxDuration) {
        this.maxDuration = maxDuration;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
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
}
