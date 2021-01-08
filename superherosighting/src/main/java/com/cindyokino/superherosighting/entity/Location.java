package com.cindyokino.superherosighting.entity;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Cindy
 */
public class Location {
    private int id;
    private String name;
    private String description;
    private String address; 
    private Double latitude;
    private Double longitude;
    private List<Hero_Villain> heroes_villainsList;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Hero_Villain> getHeroes_villainsList() {
        return heroes_villainsList;
    }

    public void setHeroes_villainsList(List<Hero_Villain> heroes_villainsList) {
        this.heroes_villainsList = heroes_villainsList;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.address);
        hash = 53 * hash + Objects.hashCode(this.latitude);
        hash = 53 * hash + Objects.hashCode(this.longitude);
        hash = 53 * hash + Objects.hashCode(this.heroes_villainsList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.heroes_villainsList, other.heroes_villainsList)) {
            return false;
        }
        return true;
    }
    
}
