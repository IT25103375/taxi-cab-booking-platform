package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

    // TODO: Replace email checks with id if necessary

    @Modifying
    @Query("""
    UPDATE Driver d
    SET d.status = 'BOOKED'
    WHERE d.id = :id
    AND d.status = 'AVAILABLE'
    """)
    int bookDriverIfAvailable(Integer id);

    @Query("""
    SELECT d FROM Driver d
    JOIN Vehicle v ON d.currentVehicleId = v.id
    WHERE d.subRegion = :subRegion
    AND d.status = 'AVAILABLE'
    AND v.vehicleType = :vehicleType
    """)
    List<Driver> findDriver(SubRegion subRegion, VehicleType vehicleType);

    @Query("""
    SELECT d FROM Driver d
    WHERE d.authEntity.email = :email
    """)
    Optional<Driver> findByEmail(String email);

    @Modifying
    @Query("""
    UPDATE Driver d
    SET d.currentVehicleId = :currentVehicleId
    WHERE d.id = :id
    AND d.status <> 'BOOKED'
    """)
    int setCurrentVehicle(Integer currentVehicleId, Integer id);

    @Modifying
    @Query("""
    UPDATE Driver d
    SET d.currentVehicleId = NULL
    WHERE d.id = :id
    AND d.currentVehicleId = :currentVehicleId
    AND d.status <> 'BOOKED'
    """)
    int removeCurrentVehicle(Integer currentVehicleId, Integer id);
}
