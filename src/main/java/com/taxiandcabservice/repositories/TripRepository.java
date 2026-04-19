package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.Trip;
import com.taxiandcabservice.enums.TripStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    @Query("""
    SELECT t FROM Trip t
    WHERE t.driver.id = :driverID
    AND t.tripStatus = 'ONGOING'
    """)
    public List<Trip> findActiveTrips(Integer driverID);

    @Query("""
    UPDATE Trip t
    SET t.tripStatus = :status
    WHERE t.id = :id
    """)
    public int updateTripStatus(TripStatus status);
}
