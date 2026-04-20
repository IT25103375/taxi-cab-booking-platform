package com.taxiandcabservice.controllers;

import com.taxiandcabservice.auth.JwtUtil;
import com.taxiandcabservice.dto.LoginRequest;
import com.taxiandcabservice.dto.RegisterRequest;
import com.taxiandcabservice.dto.TokenResponse;
import com.taxiandcabservice.entities.Passenger;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.repositories.PassengerRepository;
import com.taxiandcabservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Rest api
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/auth/register")
    public ResponseEntity<Object> addPassenger(@Valid @RequestBody RegisterRequest registerRequest) {

        try {
            return ResponseEntity.ok(userService.addUser(registerRequest));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/auth/login")
    public TokenResponse login(@RequestBody LoginRequest request) {

        return userService.login(request);
    }

    @PostMapping(path = "/auth/test")
    public String TestMethod() {
        return "test";
    }

    @GetMapping(path = "/{id}")
    public Optional<Passenger> getPassenger(@PathVariable Integer id) {
        // TODO
        return passengerRepository.findById(id);
    }

//    @PatchMapping

}
