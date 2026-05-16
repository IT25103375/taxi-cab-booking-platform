package com.taxiandcabservice.repositories;

import com.taxiandcabservice.abstracts.User;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.enums.TripStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    // FIXME: REPLACE BULK METHODS WITH MANUAL UPDATES

    @Transactional
    @Modifying
    @Query("""
    UPDATE Trip t
    SET t.tripStatus = 'CANCELLED',
        t.driver = NULL
    WHERE t.expDate <= CURRENT_TIMESTAMP
    """)
    void checkExpiredTrips();

    @Transactional
    @Modifying
    @Query("""
    UPDATE Trip t
    SET t.tripStatus = 'CANCELLED',
        t.driver = NULL
    WHERE t.expDate <= CURRENT_TIMESTAMP
    AND t.driver = :driver
    """)
    void checkExpiredTrips(Driver driver);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.startSubRegion = :startSubRegion
    AND t.tripStatus = 'REQUESTING'
    AND t.vehicleType = :vehicleType
    """)
    List<Trip> findTripRequests(SubRegion startSubRegion, VehicleType vehicleType);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.startSubRegion = :startSubRegion
    AND t.tripStatus = 'REQUESTING'
    AND t.id = :tripId
    AND t.vehicleType = :vehicleType
    """)
    Optional<Trip> findTripRequest(SubRegion startSubRegion, VehicleType vehicleType, Integer tripId);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.driver = :driver
    AND t.tripStatus IN ('PICKUP', 'ONGOING')
    """)
    Optional<Trip> findBookedTrip(Driver driver);

    List<Trip> findAllByPassenger(Passenger passenger);

    List<Trip> findAllByDriver(Driver driver);
}
