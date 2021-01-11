package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Super;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganizationById(int id);    
}