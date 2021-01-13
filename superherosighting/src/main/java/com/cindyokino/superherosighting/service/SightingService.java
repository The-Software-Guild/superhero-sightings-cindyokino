package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.SightingDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Sighting;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cindy
 */
@Service
public class SightingService {
        
    private final SightingDao sightingDao;
    
    public SightingService(SightingDao sightingDao) {
        this.sightingDao = sightingDao;
    }
    
    
    public Sighting getSightingById(int id) {
        Sighting sighting = sightingDao.getSightingById(id);
        return sighting;
    }
    
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        return sightings;
    }
    
    public Sighting addLocation(Sighting sighting) {
        Sighting newSighting = sightingDao.addSighting(sighting);
        return newSighting;
    }
    
    public void updateLocation(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }
    
    public void deleteLocationById(int id) {
        sightingDao.deleteSightingById(id);
    }
    
    public List<Sighting> getSightingsByDate(LocalDate date) {
        List<Sighting> sightings = sightingDao.getSightingsByDate(date);
        return sightings;
    }    
    
}
