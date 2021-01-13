package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.LocationDao;
import com.cindyokino.superherosighting.entity.Location;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cindy
 */
@Service
public class LocationService {
        
    private final LocationDao locationDao;
    
    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
    
    
    public Location getLocationById(int id) {
        Location location = locationDao.getLocationById(id);
        return location;
    }
    
    public List<Location> getAllLocations() {
        List<Location> locations = locationDao.getAllLocations();
        return locations;
    }
    
    public Location addLocation(Location location) {
        Location newLocation = locationDao.addLocation(location);
        return newLocation;
    }
    
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }
    
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }
    
}
