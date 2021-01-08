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
    Super getHeroVillainById(int id);
    List<Super> getAllHeroVillains();
    Super addHero_villain(Super hero_villain);
    void updateHero_Villain(Super hero_villain);
    void deleteHeroVillainById(int id);
    
    List<Super> getSupersByLocation(Location location);
    List<Super> getSupersByOrganization(Organization organization);
}