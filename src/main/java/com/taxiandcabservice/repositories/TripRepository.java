package com.taxiandcabservice.repositories;

import com.taxiandcabservice.abstracts.User;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.entities.Trip;
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
    """)
    List<Trip> findTripRequests(SubRegion startSubRegion);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.startSubRegion = :startSubRegion
    AND t.tripStatus = 'REQUESTING'
    AND t.id = :tripId
    """)
    Optional<Trip> findTripRequest(SubRegion startSubRegion, Integer tripId);
}
