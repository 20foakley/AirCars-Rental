/*package com.example.carrentalproject.service;

import com.example.carrentalproject.model.Listing;
import com.example.carrentalproject.model.User;
import com.example.carrentalproject.model.Vehicle;
import com.example.carrentalproject.repository.ListingRepository;
import com.example.carrentalproject.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListingService {



    private final ListingRepository listingRepository;
    private final VehicleRepository vehicleRepository;

    public ListingService(ListingRepository listingRepository, VehicleRepository vehicleRepository){
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing createListing(
            int vehicleId,
            User owner,
            String vin,
            float vehicle_value,
            String city,
            String country,
            String latitude,
            String longitude,
            short max_duration,
            Date created_at,
            Date updated_at,
            String title,
            String description){

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow( () -> new IllegalArgumentException("Vehicle does not exist"));

        if (listingRepository.existsByVin(vin)){
            throw new IllegalArgumentException("VIN is already registered to a listing");
        }
        Date now = new Date();

        Listing listing = new Listing();
        listing.setOwner(owner);
        listing.setVehicle(vehicle);
        //listing.setVin(vin);
        //listing.setVehicle_value(vehicleValue);
        listing.setCity(city);
        listing.setCountry(country);
        //listing.setLatitude(latitude);
        //listing.setLongitude(longitude);
        //listing.setMax_duration(maxDuration);
        listing.setTitle(title);
        listing.setDescription(description);
        //listing.setCreated_at(now);
        //listing.setUpdated_at(now);

        //return listingRepository.save(listing);

        return listing;

    }
}*/
