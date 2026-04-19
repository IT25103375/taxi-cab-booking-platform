package com.taxiandcabservice.service;

import com.taxiandcabservice.repositories.TripRepository;
import com.taxiandcabservice.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;
}
