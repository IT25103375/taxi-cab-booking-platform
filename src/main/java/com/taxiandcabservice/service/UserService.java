package com.taxiandcabservice.service;

import com.taxiandcabservice.auth.JwtUtil;
import com.taxiandcabservice.dto.LoginRequest;
import com.taxiandcabservice.dto.RegisterRequest;
import com.taxiandcabservice.dto.TokenResponse;
import com.taxiandcabservice.entities.AuthEntity;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.entities.Passenger;
import com.taxiandcabservice.enums.UserType;
import com.taxiandcabservice.repositories.AuthEntityRepository;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.repositories.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public Object addUser(RegisterRequest request) {

        // Reject invalid type or admin type(Not implemented)
        if (request.getType() == UserType.ADMIN) throw new RuntimeException("Unauthorized");

        // Reject if email exists
        if (authEntityRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("Account with email already exists");

        // Save auth info
        AuthEntity auth = new AuthEntity();
        auth.setUsername(request.getUsername());
        auth.setEmail(request.getEmail());
        auth.setEncryptedPW(passwordEncoder.encode(request.getPassword()));
        auth.setUserType(request.getType());

        // Construct and save the appropriate user type to correct repository

        if (request.getType() == UserType.PASSENGER) {

            Passenger passenger = new Passenger();
            passenger.setAuthEntity(auth);
            authEntityRepository.save(auth);
            passengerRepository.save(passenger);
            return passenger;
        }

        else if (request.getType() == UserType.DRIVER) {

            Driver driver = new Driver();
            driver.setSubRegion(regionService.findSubRegion(request.getSubRegionName()).orElseThrow());
            driver.setRegion(regionService.findRegion(request.getRegionName()).orElseThrow());

            driver.setAuthEntity(auth);
            authEntityRepository.save(auth);
            driverRepository.save(driver);
            return driver;
        }

        throw new RuntimeException("Register error");
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        TokenResponse token = new TokenResponse();

        //Verify user is in DB
        Optional<AuthEntity> opAuth = authEntityRepository.findByEmail(request.getEmail());
        if (opAuth.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), opAuth.get().getEncryptedPW())) {

            token.setSuccess(false);
            token.setError("Invalid Username or Password");
        }
        else {
            token.setToken(jwtUtil.generateToken(opAuth.get().getEmail()));
            token.setRole(opAuth.get().getUserType().name());
        }

        return token;
    }
}
