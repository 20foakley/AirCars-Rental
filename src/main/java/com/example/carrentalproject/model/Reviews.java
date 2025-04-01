package com.example.carrentalproject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import com.example.carrentalproject.model.Listings;

@Entity
@Table(name = "reviews", schema = "p2p_car_rental",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Check(constraints = "reviewer_user_id <> reviewed_user_id")
public class Reviews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_user_id")
    private Users reviewerUser;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_user_id")
    private Users reviewedUser;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id")
    private Listings listing;

    @NotNull
    @Column(length = 20)
    private String title;

    @NotNull
    @Column(length = 600)
    private String description;

    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();


// Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getReviewerUser() {
        return reviewerUser;
    }

    public void setReviewerUser(Users reviewerUser) {
        this.reviewerUser = reviewerUser;
    }

    public Users getReviewedUser() {
        return reviewedUser;
    }

    public void setReviewedUser(Users reviewedUser) {
        this.reviewedUser = reviewedUser;
    }

    public Listings getListing() {
        return listing;
    }

    public void setListing(Listings listing) {
        this.listing = listing;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}