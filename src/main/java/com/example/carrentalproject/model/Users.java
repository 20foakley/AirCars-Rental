package com.example.carrentalproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "p2p_car_rental")
public class Users implements Serializable {

    public Users(){
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;  // hashed password
        this.email = email;
    }

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

    private Float latitude;

    private Float longitude;



    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Listings> getOwnedListings() {
        return ownedListings;
    }

    public void setOwnedListings(List<Listings> ownedListings) {
        this.ownedListings = ownedListings;
    }

    public List<Rentals> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rentals> rentals) {
        this.rentals = rentals;
    }

    public List<Reviews> getGivenReviews() {
        return givenReviews;
    }

    public void setGivenReviews(List<Reviews> givenReviews) {
        this.givenReviews = givenReviews;
    }

    public List<Reviews> getReceivedReviews() {
        return receivedReviews;
    }

    public void setReceivedReviews(List<Reviews> receivedReviews) {
        this.receivedReviews = receivedReviews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRenter() {
        return isRenter;
    }

    public void setRenter(Boolean renter) {
        isRenter = renter;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}