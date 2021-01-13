package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);    
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsByDate(LocalDate date);
}
