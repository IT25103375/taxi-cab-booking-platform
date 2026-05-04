package com.taxiandcabservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taxiandcabservice.abstracts.User;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity
public class Passenger extends User {
}
