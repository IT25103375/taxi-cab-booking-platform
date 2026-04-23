package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.Passenger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PassengerRepository extends CrudRepository<Passenger, Integer> {

    @Query("""
    SELECT p FROM Passenger p
    WHERE p.authEntity.email = :email
    """)
    Optional<Passenger> findByEmail(String email);
}