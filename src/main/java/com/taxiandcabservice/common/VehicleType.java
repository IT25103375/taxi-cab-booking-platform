package com.taxiandcabservice.common;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jspecify.annotations.Nullable;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    private String name;

    private double base_fare;

    private double price_per_km;

    private Integer max_passengers;

    public @Nullable Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBase_fare() {
        return base_fare;
    }

    public void setBase_fare(double base_fare) {
        this.base_fare = base_fare;
    }

    public double getPrice_per_km() {
        return price_per_km;
    }

    public void setPrice_per_km(double price_per_km) {
        this.price_per_km = price_per_km;
    }

    public Integer getMax_passengers() {
        return max_passengers;
    }

    public void setMax_passengers(Integer max_passengers) {
        this.max_passengers = max_passengers;
    }
}
