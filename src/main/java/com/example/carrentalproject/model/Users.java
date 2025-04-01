package com.example.carrentalproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "p2p_car_rental")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //------------------------------------//
    // Relationships
    @OneToMany(mappedBy = "owner")
    private List<Listings> ownedListings;

    @OneToMany(mappedBy = "renterUser")
    private List<Rentals> rentals;

    @OneToMany(mappedBy = "reviewerUser")
    private List<Reviews> givenReviews;

    @OneToMany(mappedBy = "reviewedUser")
    private List<Reviews> receivedReviews;
    //------------------------------------//



    @NotNull
    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @NotNull
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @NotNull
    @Column(nullable = false, unique = true, length = 20)
    private String first_name;

    @NotNull
    @Column(nullable = false, length = 60)
    private String password;

    @Column(name = "is_renter")
    private Boolean isRenter;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "street_address", length = 100)
    private String streetAddress;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String stateProvince;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 50)
    private String country;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;



    // Getters and Setters

}