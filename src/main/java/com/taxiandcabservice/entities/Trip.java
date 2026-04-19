package com.taxiandcabservice.entities;

import com.taxiandcabservice.enums.TripStatus;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Date;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @OneToOne
    private Passenger passenger;

    @OneToOne
    private Driver driver;

    @OneToOne
    private SubRegion startSubRegion;

    @OneToOne
    private SubRegion destSubRegion;

    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    private Date expDate;

    public Trip() {
        tripStatus = TripStatus.UNSET;
        // Default exp of 1 day
        expDate = Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public SubRegion getDestSubRegion() {
        return destSubRegion;
    }

    public void setDestSubRegion(SubRegion destSubRegion) {
        this.destSubRegion = destSubRegion;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public SubRegion getStartSubRegion() {
        return startSubRegion;
    }

    public void setStartSubRegion(SubRegion startSubRegion) {
        this.startSubRegion = startSubRegion;
    }
}