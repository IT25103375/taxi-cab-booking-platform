package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotNull;

public class TripMinimalDTO {

    @NotNull
    private int tripId;

    public @NotNull int getTripId() {
        return tripId;
    }

    public void setTripId(@NotNull int tripId) {
        this.tripId = tripId;
    }
}
