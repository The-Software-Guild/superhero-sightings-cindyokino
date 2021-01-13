package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.OrganizationDao;
import com.cindyokino.superherosighting.entity.Organization;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cindy
 */
@Service
public class OrganizationService {
        
    private final OrganizationDao organizationDao;
    
    public OrganizationService(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }
    
    
    public Organization getOrganizationById(int id) {
        Organization organization = organizationDao.getOrganizationById(id);
        return organization;
    }
    
    public List<Organization> getAllOrganizations(){
        List<Organization> organizations = organizationDao.getAllOrganizations();
        return organizations;
    }
    
    public Organization addOrganization(Organization organization) {
        Organization newOrganization = organizationDao.addOrganization(organization);
        return newOrganization;
    }
    
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }
    
    public void deleteOrganizationById(int id) {
        organizationDao.deleteOrganizationById(id);
    }
      
}
