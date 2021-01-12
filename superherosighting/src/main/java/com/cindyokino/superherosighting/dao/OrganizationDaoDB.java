package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Organization;
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
public class OrganizationDaoDB implements OrganizationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    
     /** ********** getOrganizationById. ********** **/
    //Create the SELECT query string and use it in queryForObject to get the one Organization we are searching for.
    //Surround the code with a try-catch that will catch the exception thrown when there is no Organization with that ID, so we can return null in that situation.
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            return jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationDaoDB.OrganizationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    
    /** ********** getAllOrganizations. ********** **/
    //We simply create our SELECT query and use it in the query method to return a list of all Organizations.
    //If no Organizations are found, it will return an empty list.
    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationDaoDB.OrganizationMapper());
    }

    
    /** ********** addOrganization. ********** **/
    //This method is @Transactional so we can retrieve the new ID.
    //We write our INSERT query and use it to insert the basic Organization information into the database.
    //We get a new ID and add it to the Organization object.
    //We can then call our insertSuperOrganization helper method, which loops through the list of Supers in the Organization and adds database entries to sighting for each.
    //Once we've done that, we can return the Organization from the method.
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
       final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }
    

    /** ********** updateOrganization. ********** **/
    //Use of @Transactional here because we are making multiple database-modifying queries in the method.
    //Write the UPDATE query and use it in the update method with the appropriate data.
    //We need to handle the Organization by first deleting all the super_organization entries and then adding them back in with the call to insertSuperOrganization.
    @Override
    @Transactional
    public void updateOrganization(Organization organization) {        
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, "
                + "address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION, 
                organization.getName(), 
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getId());
    }

    
    /** ********** deleteOrganizationById. ********** **/
    //This is @Transactional because of the multiple database-modifying queries.
    //First, we get rid of the super_organization entries that reference our Organization.
    //Then we can delete the organization itself.
    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_organization WHERE organization_id = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, id);
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }
    
    
    /** ********** Get data from database with Mapper function. ********** **/
     public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContact(rs.getString("contact"));
            return organization;
        }
    }
    
}
