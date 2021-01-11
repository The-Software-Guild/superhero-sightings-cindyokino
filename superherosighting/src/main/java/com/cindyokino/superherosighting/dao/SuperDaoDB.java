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
import java.util.List;
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
            Super hero_villain = jdbc.queryForObject(SELECT_SUPER_BY_ID, new SuperMapper(), id);
            hero_villain.setLocations(getLocationsForSuper(id)); //set list of locations
            hero_villain.setPowers(getPowersForSuper(id)); //set list of powers
            hero_villain.setOrganizations(getOrganizationsForSuper(id)); //set list of organizations
            return hero_villain;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    //JOIN from Location to sighting to get a list of Location objects for this Super.
    private List<Location> getLocationsForSuper(int id) {
        final String SELECT_LOCATIONS_FOR_SUPER = "SELECT l.* FROM location l "
                + "JOIN sighting si ON si.location_id = l.id WHERE si.super_id = ?";
        return jdbc.query(SELECT_LOCATIONS_FOR_SUPER, new LocationMapper(), id);
    } 

    //JOIN from Power to super_power to get a list of Power objects for this Super
    private List<Power> getPowersForSuper(int id) {
        final String SELECT_POWERS_FOR_SUPER = "SELECT p.* FROM power p "
                + "JOIN super_power sp ON sp.power_id = p.id WHERE sp.super_id = ?";
        return jdbc.query(SELECT_POWERS_FOR_SUPER, new PowerMapper(), id);    
    }
    
    //JOIN from Location to super_organization to get a list of Organization objects for this Super
    private List<Organization> getOrganizationsForSuper(int id) {
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
        for (Super hero_villain : supers) {
            hero_villain.setPowers(getPowersForSuper(hero_villain.getId()));
            hero_villain.setOrganizations(getOrganizationsForSuper(hero_villain.getId()));
            hero_villain.setLocations(getLocationsForSuper(hero_villain.getId()));
        }
    }

    
    /** ********** addSuper. ********** **/
    //Just like the previous add method, this is @Transactional so we can retrieve the new ID.
    //We write our INSERT query and use it to insert the basic Super information into the database.
    //We get a new ID and add it to the Super object.
    //We can then call our insertSighting helper method, which loops through the list of Locations in the Super and adds database entries to sighting for each.
    //We can then call our insertPower helper method, which loops through the list of Powers in the Super and adds database entries to super_power for each.
    //We can then call our insertOrganization helper method, which loops through the list of Organizations in the Super and adds database entries to super_organization for each.
    //Once we've done that, we can return the Course from the method.
    @Override
    @Transactional
    public Super addSuper(Super hero_villain) {
        final String INSERT_SUPER = "INSERT INTO super(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPER,
                hero_villain.getName(),
                hero_villain.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero_villain.setId(newId);
        insertSighting(hero_villain);
        insertPower(hero_villain);
        insertOrganization(hero_villain);
        return hero_villain;
    }  
    
    //Loops through the list of Locations in the Super and adds database entries to sighting for each
    private void insertSighting(Super super_villain) {
        final String INSERT_SIGHTING = "INSERT INTO "
                + "sighting(super_id, location_id) VALUES(?,?)";
        for(Location location : super_villain.getLocations()) {
            jdbc.update(INSERT_SIGHTING, 
                    super_villain.getId(),
                    location.getId());
        }
    }
    
    //Loops through the list of Powers in the Super and adds database entries to super_power for each.
    private void insertPower(Super super_villain) {
        final String INSERT_POWER = "INSERT INTO "
                + "super_power(super_id, power_id) VALUES(?,?)";
        for(Power power : super_villain.getPowers()) {
            jdbc.update(INSERT_POWER, 
                    super_villain.getId(),
                    power.getId());
        }
    }
    
    //Loops through the list of Organizations in the Super and adds database entries to super_organization for each.
    private void insertOrganization(Super super_villain) {
        final String INSERT_ORGANIZATION = "INSERT INTO "
                + "super_organization(super_id, organization_id) VALUES(?,?)";
        for(Organization organization : super_villain.getOrganizations()) {
            jdbc.update(INSERT_ORGANIZATION, 
                    super_villain.getId(),
                    organization.getId());
        }
    }
    
    
    /** ********** updateSuper. ********** **/
    //We are using @Transactional here because we are making multiple database-modifying queries in the method.
    //We write our UPDATE query and use it in the update method with the appropriate data.
    //We need to handle the Sightings by first deleting all the sighting entries and then adding them back in with the call to insertSighting.
    //We need to handle the Powers by first deleting all the super_power entries and then adding them back in with the call to insertPower.
    //We need to handle the Organizations by first deleting all the super_organization entries and then adding them back in with the call to insertOrganization.
    @Override
    @Transactional
    public void updateSuper(Super hero_villain) {
        final String UPDATE_SUPER = "UPDATE super SET name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPER, 
                hero_villain.getName(), 
                hero_villain.getDescription(), 
                hero_villain.getId());
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE super_id = ?";
        jdbc.update(DELETE_SIGHTING, hero_villain.getId());        
        insertOrganization(hero_villain);
        
        final String DELETE_SUPER_POWER = "DELETE FROM sighting WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_POWER, hero_villain.getId());
        insertPower(hero_villain);
        
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM sighting WHERE super_id = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, hero_villain.getId());
        insertSighting(hero_villain);
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
        associatePowerOrganizationSighting(supers);
        return supers;
    }

    
    /** ********** getSupersByOrganization. ********** **/
    //We JOIN with super_location so we can limit the query based on the organization_id. 
    //Once we have the list, we use associatePowerOrganizationSighting to fill in the rest of the data.
    @Override
    public List<Super> getSupersByOrganization(Organization organization) {
        final String SELECT_SUPERS_FOR_LOCATION = "SELECT s.* FROM super s JOIN "
                + "super_organization so ON so.super_id = s.id WHERE so.orgaization_id = ?";
        List<Super> supers = jdbc.query(SELECT_SUPERS_FOR_LOCATION, 
                new SuperMapper(), organization.getId());
        associatePowerOrganizationSighting(supers);
        return supers;
    }
    
    
    public static final class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super hero_villain = new Super();
            hero_villain.setId(rs.getInt("id"));
            hero_villain.setName(rs.getString("name"));
            hero_villain.setDescription(rs.getString("description"));            
            return hero_villain;
        }
    }
    
}


