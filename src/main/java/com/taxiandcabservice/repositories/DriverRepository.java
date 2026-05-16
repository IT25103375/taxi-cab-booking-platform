package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

    @Query("""
    SELECT d FROM Driver d
    WHERE d.authEntity.email = :email
    """)
    Optional<Driver> findByAuthEntity_Email(String email);
}
