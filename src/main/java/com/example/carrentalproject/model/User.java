package com.example.carrentalproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "p2p_car_rental")
public class User implements UserDetails, Serializable {

    public User(){
    }



    public User(String username, String password , String email) {
        this.username = username;
        this.password  = password ;  // hashed password
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //------------------------------------//
    // Relationships
    // These add overhead but help access things that a user might have associated with them.
    @OneToMany(mappedBy = "owner")
    private List<Listing> ownedListings;

    @OneToMany(mappedBy = "renter")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> givenReviews;

    @OneToMany(mappedBy = "reviewee")
    private List<Review> receivedReviews;
    //------------------------------------//



    @NotNull
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @NotNull
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(name="first_name", length = 30)
    private String firstName;

    @Column(name="last_name", length = 40)
    private String lastName;

    @NotNull
    @Column(name="password", nullable = false, length = 255)
    private String password ;

    @NotNull
    @Column(nullable=false,name = "is_renter")
    private Boolean isRenter=false;

    @NotNull
    @Column(nullable=false,name = "is_admin")
    private Boolean isAdmin=false;

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

    @DecimalMin(value = "-90.0", message = "Latitude cannot be less than -90")
    @DecimalMax(value = "90.0", message = "Latitude cannot be greater than 90")
    @Column(precision = 8, scale = 6)
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0", message = "Longitude cannot be less than -180")
    @DecimalMax(value = "180.0", message = "Longitude cannot be greater than 180")
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotNull
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void PreUpdate(){
        this.updatedAt = OffsetDateTime.now();
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Listing> getOwnedListings() {
        return ownedListings;
    }

    public void setOwnedListings(List<Listing> ownedListings) {
        this.ownedListings = ownedListings;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Review> getGivenReviews() {
        return givenReviews;
    }

    public void setGivenReviews(List<Review> givenReviews) {
        this.givenReviews = givenReviews;
    }

    public List<Review> getReceivedReviews() {
        return receivedReviews;
    }

    public void setReceivedReviews(List<Review> receivedReviews) {
        this.receivedReviews = receivedReviews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> isAdmin ? "ROLE_ADMIN" : "ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}