package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, Integer> {
}
