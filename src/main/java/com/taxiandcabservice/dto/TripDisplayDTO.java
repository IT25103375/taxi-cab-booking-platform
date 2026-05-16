package com.taxiandcabservice.dto;

import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.enums.TripStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class TripDisplayDTO {

    @NotNull
    private Integer id;

    @NotBlank
    private String passengerName;

    @NotBlank
    private String driverName;

    @NotBlank
    private String startAddress;

    @NotBlank
    private String startRegion;

    @NotBlank
    private String startSubRegion;

    @NotBlank
    private String destAddress;

    @NotBlank
    private String destRegion;

    @NotBlank
    private String destSubRegion;

    @NotBlank
    private String vehicleType;

    @NotBlank
    private TripStatus tripStatus;

    @NotNull
    private double tripFare;

    private Date expDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTripFare() {
        return tripFare;
    }

    public void setTripFare(double tripFare) {
        this.tripFare = tripFare;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartRegion() {
        return startRegion;
    }

    public void setStartRegion(String startRegion) {
        this.startRegion = startRegion;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getDestRegion() {
        return destRegion;
    }

    public void setDestRegion(String destRegion) {
        this.destRegion = destRegion;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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

    public String getStartSubRegion() {
        return startSubRegion;
    }

    public void setStartSubRegion(String startSubRegion) {
        this.startSubRegion = startSubRegion;
    }

    public String getDestSubRegion() {
        return destSubRegion;
    }

    public void setDestSubRegion(String destSubRegion) {
        this.destSubRegion = destSubRegion;
    }
}