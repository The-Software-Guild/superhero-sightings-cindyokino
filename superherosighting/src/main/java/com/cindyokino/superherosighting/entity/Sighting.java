package com.cindyokino.superherosighting.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Cindy
 */
public class Sighting {
    private Super hero_villain;
    private Location location;
    private LocalDate date;

    
    public Super getHero_villain() {
        return hero_villain;
    }

    public void setHero_villain(Super hero_villain) {
        this.hero_villain = hero_villain;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.hero_villain);
        hash = 59 * hash + Objects.hashCode(this.location);
        hash = 59 * hash + Objects.hashCode(this.date);
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
        final Sighting other = (Sighting) obj;
        if (!Objects.equals(this.hero_villain, other.hero_villain)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
}
