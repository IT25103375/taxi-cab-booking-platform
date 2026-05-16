package com.taxiandcabservice.entities;

import com.taxiandcabservice.enums.TripStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @NotNull
    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Driver driver;

    @NotNull
    private String startAddress;

    @NotNull
    private String destAddress;

    @NotNull
    @ManyToOne
    private SubRegion startSubRegion;

    @NotNull
    @ManyToOne
    private SubRegion destSubRegion;

    @Enumerated(EnumType.ORDINAL)
    @ManyToOne
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    private double tripFare;

    private Date expDate;

    public Trip() {
        tripStatus = TripStatus.REQUESTING;
        // Default exp of 1 day
        expDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    }

    public double getTripFare() {
        return tripFare;
    }

    public void setTripFare(double tripFare) {
        this.tripFare = tripFare;
    }

    public @Nullable Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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