package com.taxiandcabservice.controllers;

import com.taxiandcabservice.abstracts.User;
import com.taxiandcabservice.auth.JwtUtil;
import com.taxiandcabservice.dto.LoginRequest;
import com.taxiandcabservice.dto.RegisterRequest;
import com.taxiandcabservice.dto.TokenResponse;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.entities.Passenger;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.repositories.PassengerRepository;
import com.taxiandcabservice.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Rest api
@CrossOrigin
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
    public ResponseEntity<Object> addUser(@Valid @RequestBody RegisterRequest registerRequest) {

        try {
            return ResponseEntity.ok(userService.addUser(registerRequest));
        }
        catch (RuntimeException e) {
            throw e; // lmao
//            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/auth/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }

    @GetMapping(path = "/auth/test")
    public ResponseEntity<String> TestMethod() {
        return ResponseEntity.ok("Success");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {

        // TODO: Implement dto

        Optional<Passenger> passenger = passengerRepository.findById(id);
        Optional<Driver> driver = driverRepository.findById(id);

        return passenger.<ResponseEntity<User>>map(ResponseEntity::ok).orElseGet(()
                -> driver.<ResponseEntity<User>>map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build()));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/logintest")
    public ResponseEntity<Object> testLogin() {

        return ResponseEntity.ok(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
    }

//    @PatchMapping

}
