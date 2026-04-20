package com.taxiandcabservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Region {
    @Id
    @GeneratedValue
    private @Nullable Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubRegion> subRegions = new ArrayList<>();

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String displayName;

    public Integer getId() {
        return id;
    }

    public Region setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Region setName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Region setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public List<SubRegion> getSubRegions() {
        return subRegions;
    }

    public void setSubRegions(List<SubRegion> subRegions) {
        this.subRegions = subRegions;
    }
}
