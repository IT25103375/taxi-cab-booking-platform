package com.taxiandcabservice.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PassengerRepository extends CrudRepository<Passenger, Integer> {

    Optional<Passenger> findByEmail(String email);
}