package com.example.carrentalproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="rentals", schema="p2p_car_rental")
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //------------------------------------//
    // Relationships
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_user_id")
    private Users renterUser;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    // had this as an int instead of Users before which missed the point of this being an abstraction of db
    private Users ownerUser;
    //------------------------------------//

    @NotNull
    @JoinColumn(name = "vehicle_value")
    private float vehicleValue; // float = real

    @NotNull
    @Column(length = 50)
    private String city;

    @NotNull
    @Column(length = 50)
    private String country;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    @NotNull
    private short duration;

    @NotNull
    @JoinColumn(name = "created_at")
    private Date createdAt;

    @NotNull
    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    @NotNull
    @Column(length=20)
    private String title;

    @NotNull
    @Column(length=20)
    private String description;




    // Getters and Setters


}
