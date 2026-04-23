package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
}
