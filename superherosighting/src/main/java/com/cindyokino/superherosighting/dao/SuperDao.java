package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface SuperDao {
    Super getSuperById(int id);
    List<Super> getAllSupers();
    Super addSuper(Super hero_villain);
    void updateSuper(Super hero_villain);
    void deleteSuperById(int id);
    
    List<Super> getSupersByLocation(Location location);
    List<Super> getSupersByOrganization(Organization organization);
}