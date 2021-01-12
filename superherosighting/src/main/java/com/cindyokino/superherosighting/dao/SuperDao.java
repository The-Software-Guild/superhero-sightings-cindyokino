package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface SuperDao {
    Super getSuperById(int id);
    List<Super> getAllSupers();
    Super addSuper(Super heroVillain);
    void updateSuper(Super heroVillain);
    void deleteSuperById(int id);
    
    List<Super> getSupersByLocation(Location location);
    List<Super> getSupersByOrganization(Organization organization);
    
    List<Location> getLocationsForSuper(int id);
    List<Power> getPowersForSuper(int id);
    List<Organization> getOrganizationsForSuper(int id);
    
    void insertSighting(Super heroVillain);
    void insertPower(Super heroVillain);
    void insertOrganization(Super heroVillain);
    
    void removePowerForSuper(int superId, int powerId);
    void removeOrganizationForSuper(int superId, int organizationId);
}