package com.example.carrentalproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
@Entity
@Table(name="rentals", schema="p2p_car_rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //------------------------------------//
    // Relationships
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "renter_user_id")
    private User renter;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listing_id")
    private Listing listing;
    //------------------------------------//

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @NotNull
    @DecimalMin(value = "10.00", message = "Daily fee must be at least 10")
    @Column(name = "daily_fee", precision = 10, scale = 2)
    private BigDecimal dailyFee;

    @NotNull
    @DecimalMin(value = "0.0001", message = "Fee percentage must be greater than 0")
    @DecimalMax(value = "1.0", message = "Fee percentage must be at most 1")
    @Column(name = "fee_percentage", precision = 5, scale = 4)
    private BigDecimal feePercentage;

    @DecimalMin(value = "10.00", message = "Deposit must be at least 10")
    @Column(name = "deposit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @NotNull
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @NotNull
    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // Runs once from JPA provider before entity is inserted into database
    @PrePersist
    public void PrePersist() {
    // Handle validations that we can't handle using annotations
        if(this.startDate.isAfter(this.endDate)  || this.startDate.isEqual(this.endDate)){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (renter.equals(owner)){
            throw new IllegalArgumentException("Renter cannot be the owner of this rental");
        }
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(BigDecimal dailyFee) {
        this.dailyFee = dailyFee;
    }

    public BigDecimal getFeePercentage() {
        return feePercentage;
    }

    public void setFeePercentage(BigDecimal feePercentage) {
        this.feePercentage = feePercentage;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
