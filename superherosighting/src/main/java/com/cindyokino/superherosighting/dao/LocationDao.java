package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Super;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
    
    List<Location> getLocationBySuper(Super hero_villain);
}