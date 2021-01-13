package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.SuperDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Super;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cindy
 */
@Service
public class SuperService {
    
    private final SuperDao superDao;
    
    public SuperService(SuperDao superDao){
        this.superDao = superDao;
    }
    
    
    public Super getSuperById(int id) {
        Super supper = superDao.getSuperById(id);
        return supper;
    }
    
    public List<Super> getAllSupers() {
        List<Super> supers = superDao.getAllSupers();
        return supers;
    }    
    
    public Super addSuper(Super heroVillain) {
        Super supper = superDao.addSuper(heroVillain);
        return supper;
    }
    
    public void updateSuper(Super heroVillain) {
        superDao.updateSuper(heroVillain);
    }
    
    public void deleteSuperById(int id) {
        superDao.deleteSuperById(id);
    }
    
    public List<Super> getSupersByLocation(Location location) {
        List<Super> supers = superDao.getSupersByLocation(location);
        return supers;
    }
    
    public List<Super> getSupersByOrganization(Organization organization) {
        List<Super> supers = superDao.getSupersByOrganization(organization);
        return supers;
    }
    
    public List<Location> getLocationsForSuper(int id) {
        List<Location> locations = superDao.getLocationsForSuper(id);
        return locations;
    }
    
    public List<Power> getPowersForSuper(int id) {
        List<Power> powers = superDao.getPowersForSuper(id);
        return powers;
    }
    
    public List<Organization> getOrganizationsForSuper(int id) {
        List<Organization> organizations = superDao.getOrganizationsForSuper(id);
        return organizations;
    }
    
    public void removePowerForSuper(int superId, int powerId) {
        superDao.removePowerForSuper(superId, powerId);
    }
    
    public void removeOrganizationForSuper(int superId, int organizationId) {
        superDao.removeOrganizationForSuper(superId, organizationId);
    }   
    
}
