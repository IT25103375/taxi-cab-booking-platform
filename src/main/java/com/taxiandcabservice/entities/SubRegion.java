package com.taxiandcabservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

@Entity
public class SubRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String displayName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subRegionId")
    private Region region;

    public Integer getId() {
        return id;
    }

    public SubRegion setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubRegion setName(String name) {
        this.name = name;
        return this;
    }

    public Region getRegion() {
        return region;
    }

    public SubRegion setRegion(Region region) {
        this.region = region;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SubRegion setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
