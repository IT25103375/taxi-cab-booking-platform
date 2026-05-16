package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

    @Query("""
    SELECT v FROM Vehicle v
    WHERE v.driver.id = :driverId
    """)
    List<Vehicle> findByDriver_Id(int driverId);
}
