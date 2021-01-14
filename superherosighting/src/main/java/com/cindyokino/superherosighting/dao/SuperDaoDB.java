package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.dao.LocationDaoDB.LocationMapper;
import com.cindyokino.superherosighting.dao.OrganizationDaoDB.OrganizationMapper;
import com.cindyokino.superherosighting.dao.PowerDaoDB.PowerMapper;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cindy
 */
@Repository
public class SuperDaoDB implements SuperDao{
    
    @Autowired
    JdbcTemplate jdbc;
    OrganizationDaoDB organizationDaoDb;
    LocationDaoDB locationDaoDb;
    PowerDaoDB powerDaodb;
    SightingDaoDB sightingDaoDb;

    
    /** ********** getSuperById. ********** **/
    //Wrap the code of the getSuperById method in a try-catch in case the Super does not exist.
    //Start with a SELECT query to get the basic Super object.
    //Then call the getLocationsForSuper method, where we JOIN from Location to sighting to get a list of Location objects for this Super.
    //Then call the getPowersForSuper method, where we JOIN from Power to super_power to get a list of Power objects for this Super.
    //Then call the getOrganizationsForSuper method, where we JOIN from Location to super_organization to get a list of Organization objects for this Super.
    //If we catch an exception in getSuperById, we return null because it means the Super does not exist.
    @Override
    public Super getSuperById(int id) {
        try {
            final String SELECT_SUPER_BY_ID = "SELECT * FROM super WHERE id = ?";
            Super heroVillain = jdbc.queryForObject(SELECT_SUPER_BY_ID, new SuperMapper(), id);
            heroVillain.setLocations(getLocationsForSuper(id)); //set list of locations
            heroVillain.setPowers(getPowersForSuper(id)); //set list of powers
            heroVillain.setOrganizations(getOrganizationsForSuper(id)); //set list of organizations
            return heroVillain;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    //JOIN from Location to sighting to get a list of Location objects for this Super.
    @Override
    public List<Location> getLocationsForSuper(int id) {       
        
        final String SELECT_LOCATIONS_FOR_SUPER = "SELECT l.* FROM location l "
                + "JOIN sighting si ON si.location_id = l.id WHERE si.super_id = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPER, new LocationMapper(), id);
        
        Set<Location> set = new HashSet<>(locations); //remove duplicate locations from list
        locations.clear();
        locations.addAll(set);

        return locations;
    } 

    //JOIN from Power to super_power to get a list of Power objects for this Super
    @Override
    public List<Power> getPowersForSuper(int id) {
        final String SELECT_POWERS_FOR_SUPER = "SELECT p.* FROM power p "
                + "JOIN super_power sp ON sp.power_id = p.id WHERE sp.super_id = ?";
        return jdbc.query(SELECT_POWERS_FOR_SUPER, new PowerMapper(), id);    
    }
    
    //JOIN from Location to super_organization to get a list of Organization objects for this Super
    @Override
    public List<Organization> getOrganizationsForSuper(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPER = "SELECT o.* FROM organization o "
                + "JOIN super_organization so ON so.organization_id = o.id WHERE so.super_id = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPER, new OrganizationMapper(), id); 
    }
    
    /** ********** getAllSupers. ********** **/
    //We start by writing a SELECT query and using it to get the list of Supers.
    //We pass the list of Supers into associatePowerOrganizationSighting, where we loop through the list and call our existing Location, Power and Organization methods to fill in the data for each Super.
    @Override
    public List<Super> getAllSupers() {
        final String SELECT_ALL_SUPERS = "SELECT * FROM super";
        List<Super> supers = jdbc.query(SELECT_ALL_SUPERS, new SuperMapper());
        associatePowerOrganizationSighting(supers);
        return supers;
    }
    
    private void associatePowerOrganizationSighting(List<Super> supers ) {
        for (Super heroVillain : supers) {
            heroVillain.setPowers(getPowersForSuper(heroVillain.getId()));
            heroVillain.setOrganizations(getOrganizationsForSuper(heroVillain.getId()));
            heroVillain.setLocations(getLocationsForSuper(heroVillain.getId()));
        }
    }

    
    /** ********** addSuper. ********** **/
    //Just like the previous add method, this is @Transactional so we can retrieve the new ID.
    //We write our INSERT query and use it to insert the basic Super information into the database.
    //We get a new ID and add it to the Super object.
    //We can then call our insertPower helper method, which loops through the list of Powers in the Super and adds database entries to super_power for each.
    //We can then call our insertOrganization helper method, which loops through the list of Organizations in the Super and adds database entries to super_organization for each.
    //Once we've done that, we can return the Course from the method.
    @Override
    @Transactional
    public Super addSuper(Super heroVillain) {
        final String INSERT_SUPER = "INSERT INTO super(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPER,
                heroVillain.getName(),
                heroVillain.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        heroVillain.setId(newId);

        if(heroVillain.getPowers() != null && !heroVillain.getPowers().isEmpty()) {        
            insertPower(heroVillain);
        }
        if(heroVillain.getOrganizations() != null && !heroVillain.getOrganizations().isEmpty()) {
            insertOrganization(heroVillain);
        }
        return heroVillain;
    }  
    
    
    //Loops through the list of Powers in the Super and adds database entries to super_power for each.
    private void insertPower(Super heroVillain) {
        final String INSERT_POWER = "INSERT INTO "
                + "super_power(super_id, power_id) VALUES(?,?)";
        for(Power power : heroVillain.getPowers()) {
            jdbc.update(INSERT_POWER, 
                    heroVillain.getId(),
                    power.getId());
        }
    }
    
    //Loops through the list of Organizations in the Super and adds database entries to super_organization for each.
    private void insertOrganization(Super heroVillain) {
        final String INSERT_ORGANIZATION = "INSERT INTO "
                + "super_organization(super_id, organization_id) VALUES(?,?)";
        for(Organization organization : heroVillain.getOrganizations()) {
            jdbc.update(INSERT_ORGANIZATION, 
                    heroVillain.getId(),
                    organization.getId());
        }
    }
    
    
    /** ********** updateSuper. ********** **/
    //We are using @Transactional here because we are making multiple database-modifying queries in the method.
    //We write our UPDATE query and use it in the update method with the appropriate data.
    //We need to handle the Powers by first deleting all the super_power entries and then adding them back in with the call to insertPower.
    //We need to handle the Organizations by first deleting all the super_organization entries and then adding them back in with the call to insertOrganization.
    @Override
    @Transactional
    public void updateSuper(Super heroVillain) {
        final String UPDATE_SUPER = "UPDATE super SET name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPER, 
                heroVillain.getName(), 
                heroVillain.getDescription(), 
                heroVillain.getId());
        
        final String DELETE_SUPER_POWER = "DELETE FROM super_power WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_POWER, heroVillain.getId());
        insertPower(heroVillain);
        
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_organization WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, heroVillain.getId());        
        insertOrganization(heroVillain);
    }

    
    /** ********** deleteSuperById. ********** **/
    //This is @Transactional because of the multiple database-modifying queries.
    //First, we get rid of the sighting entries that reference our Super.
    //Second, we get rid of the super_power entries that reference our Super.
    //Third, we get rid of the super_organization entries that reference our Super.
    //Then we can delete the super itself.
    @Override
    @Transactional
    public void deleteSuperById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE super_id = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_SUPER_POWER = "DELETE FROM super_power WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_POWER, id);
        
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_organization WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, id);
        
        final String DELETE_SUPER = "DELETE FROM super WHERE id = ?";
        jdbc.update(DELETE_SUPER, id);
    }

    
    /** ********** getSupersByLocation. ********** **/
    //We JOIN with super_location so we can limit the query based on the location_id. 
    //Once we have the list, we use associatePowerOrganizationSighting to fill in the rest of the data.
    @Override
    public List<Super> getSupersByLocation(Location location) {
        final String SELECT_SUPERS_FOR_LOCATION = "SELECT s.* FROM super s JOIN "
                + "sighting sig ON sig.super_id = s.id WHERE sig.location_id = ?";
        List<Super> supers = jdbc.query(SELECT_SUPERS_FOR_LOCATION, 
                new SuperMapper(), location.getId());
        
        Set<Super> set = new HashSet<>(supers); //remove duplicate supers from list
        supers.clear();
        supers.addAll(set);

        associatePowerOrganizationSighting(supers);
        return supers;
    }

    
    /** ********** getSupersByOrganization. ********** **/
    //We JOIN with super_location so we can limit the query based on the organization_id. 
    //Once we have the list, we use associatePowerOrganizationSighting to fill in the rest of the data.
    @Override
    public List<Super> getSupersByOrganization(Organization organization) {
        final String SELECT_SUPERS_FOR_LOCATION = "SELECT s.* FROM super s JOIN "
                + "super_organization so ON so.super_id = s.id WHERE so.organization_id = ?";
        List<Super> supers = jdbc.query(SELECT_SUPERS_FOR_LOCATION, 
                new SuperMapper(), organization.getId());
        associatePowerOrganizationSighting(supers);
        return supers;
    }

    
    /** ********** removePowerForSuper. ********** **/
    // Remove the link between a super and a power
    @Override
    public void removePowerForSuper(int superId, int powerId) {
        final String DELETE_POWER_FOR_SUPER = "DELETE FROM super_power WHERE super_id = ? AND power_id = ?";
        jdbc.update(DELETE_POWER_FOR_SUPER, superId, powerId);
    }
    
    
    /** ********** removeOrganizationForSuper. ********** **/
    // Remove the link between a super and an organization
    @Override
    public void removeOrganizationForSuper(int superId, int organizationId) {
        final String DELETE_ORGANIZATION_FOR_SUPER = "DELETE FROM super_organization WHERE super_id = ? AND organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION_FOR_SUPER, superId, organizationId);
    }
    
    
    public static final class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super heroVillain = new Super();
            heroVillain.setId(rs.getInt("id"));
            heroVillain.setName(rs.getString("name"));
            heroVillain.setDescription(rs.getString("description"));   
        
            return heroVillain;
        }
    }
    
}


