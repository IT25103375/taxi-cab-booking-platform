package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotNull;

public class TripMinimalFareDTO {

    @NotNull
    private int tripId;

    private double tripKM;

    public double getTripKM() {
        return tripKM;
    }

    public void setTripKM(double tripKM) {
        this.tripKM = tripKM;
    }

    public @NotNull int getTripId() {
        return tripId;
    }

    public void setTripId(@NotNull int tripId) {
        this.tripId = tripId;
    }
}
