package com.taxiandcabservice.service;

import com.taxiandcabservice.abstracts.User;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private RegionService regionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public Object addUser(RegisterRequest request) {

        // Reject invalid type or admin type(Not implemented)
        if (request.getUserType() == UserType.ADMIN) throw new RuntimeException("Unauthorized");

        // Reject if email exists
        if (authEntityRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("Account with email already exists");

        // Save auth info
        AuthEntity auth = new AuthEntity();
        auth.setUsername(request.getUsername());
        auth.setEmail(request.getEmail());
        auth.setPassword(passwordEncoder.encode(request.getPassword()));
        auth.setUserType(request.getUserType());

        // Construct and save the appropriate user type to correct repository

        if (request.getUserType() == UserType.PASSENGER) {

            Passenger passenger = new Passenger();
            passenger.setAuthEntity(auth);
            auth.setPassenger(passenger);
            authEntityRepository.save(auth);
            passengerRepository.save(passenger);
            return passenger;
        }

        else if (request.getUserType() == UserType.DRIVER) {

            Driver driver = new Driver();
            driver.setSubRegion(regionService.findSubRegion(request.getSubRegionId()).orElseThrow());
            driver.setRegion(regionService.findRegion(request.getRegionId()).orElseThrow());

            driver.setAuthEntity(auth);
            auth.setDriver(driver);
            authEntityRepository.save(auth);
            driverRepository.save(driver);
            return driver;
        }

        throw new RuntimeException("Unused_Register error");
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        TokenResponse token = new TokenResponse();

        //Verify user is in DB
        Optional<AuthEntity> opAuth = authEntityRepository.findByEmail(request.getEmail());
        if (opAuth.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), opAuth.get().getPassword())) {

            token.setSuccess(false);
            token.setError("Invalid Username or Password");
        }
        else {

            token.setSuccess(true);
            token.setToken(jwtUtil.generateToken(opAuth.get().getEmail()));
            token.setRole(opAuth.get().getUserType().name());
        }

        return token;
    }

    // TODO: Implement logout with a blacklist cache since JWT is stateless

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @Transactional
    public Passenger getCurrentPassenger() throws EntityNotFoundException {
        return passengerRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getEmail())
                .orElseThrow(() -> new EntityNotFoundException("No such passenger"));
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public Driver getCurrentDriver() throws EntityNotFoundException {
        return driverRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getEmail())
                .orElseThrow(() -> new EntityNotFoundException("No such driver"));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER')")
    @Transactional
    public Integer[] getCurrentUser() throws EntityNotFoundException {

        Optional<Driver> opDriver = driverRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getEmail());
        Optional<Passenger> opPassenger = passengerRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getEmail());

        return new Integer[]{
                opPassenger.map(User::getId).orElse(null),
                opDriver.map(User::getId).orElse(null)
        };
    }
}
