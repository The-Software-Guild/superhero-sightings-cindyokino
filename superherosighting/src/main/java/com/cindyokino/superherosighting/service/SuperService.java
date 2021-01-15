package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.SuperDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Super;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Cindy
 */
@Service
public class SuperService {
    
    private final SuperDao superDao;
    
    private final PowerService powerService;
    private final OrganizationService organizationService;
    
    
    public SuperService(SuperDao superDao, PowerService powerService, OrganizationService organizationService){
        this.powerService = powerService;
        this.organizationService = organizationService;
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
    
    public Super addSuper(Super heroVillain, List<String> powerIds, List<String> organizationIds, byte[] superImage) {
        List<Power> powers = new ArrayList<>();
        for(String powerId : powerIds) {
            powers.add(powerService.getPowerById(Integer.parseInt(powerId)));
        }
        
        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
           organizations.add(organizationService.getOrganizationById(Integer.parseInt(organizationId)));
        }
        
        heroVillain.setPowers(powers);
        heroVillain.setOrganizations(organizations);
        heroVillain.setSuperImage(superImage);
        
        Super supper = superDao.addSuper(heroVillain);
        return supper;
    }
    
    public void updateSuper(Super heroVillain, List<String> powerIds, List<String> organizationIds) {
        List<Power> powers = new ArrayList<>();
        for(String powerId : powerIds) {
            powers.add(powerService.getPowerById(Integer.parseInt(powerId)));
        }
        
        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
           organizations.add(organizationService.getOrganizationById(Integer.parseInt(organizationId)));
        }
        
        heroVillain.setPowers(powers);
        heroVillain.setOrganizations(organizations);
        
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
    
//    public void saveImageFile(Integer id, MultipartFile file) {
//        try {
//            Super supper = superDao.getSuperById(id);
//            byte[] byteObjects = new byte[file.getBytes().length];
//            
//            int i = 0;
//            
//            for (byte b : file.getBytes()) {
//                byteObjects[i++] = b;
//            }
//            
//            supper.setSuperImage(byteObjects);
//            
//            superDao.addSuper(supper);
//        } catch (IOException e) {
////            log.error("Error occurred", e);
//            e.printStackTrace();
//        }        
//        System.out.println("RECEIVED A FILE!!");
//    }
    
}
