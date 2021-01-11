package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Sighting;
import com.cindyokino.superherosighting.entity.Super;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Cindy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {
    
    //Autowire in the DAO classes:
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    SightingDao sightingDao;
            
    @Autowired
    SuperDao superDao;
    
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
        List<Power> powers = powerDao.getAllPowers();
        for(Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        
        List<Super> supers = superDao.getAllSupers();
        for(Super hero_villain : supers) {
            superDao.deleteSuperById(hero_villain.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    
    /**     --------------------------- TESTS ---------------------------.     */
    /**
     * Test of addAndGetOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganization() {
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        
        assertEquals(organization, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization organization1 = new Organization();
        organization1.setName("Test Name");
        organization1.setDescription("Test Description");
        organization1.setAddress("Test Address");
        organization1.setContact("Test Contact");
        organization1 = organizationDao.addOrganization(organization1);
        
        Organization organization2 = new Organization();
        organization2.setName("Test Name");
        organization2.setDescription("Test Description");
        organization2.setAddress("Test Address");
        organization2.setContact("Test Contact");
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
       Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
        
        organization.setName("New Name");
        organizationDao.updateOrganization(organization);
        
        assertNotEquals(organization, fromDao);
        
        fromDao = organizationDao.getOrganizationById(organization.getId());
        
        assertEquals(organization, fromDao);
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Organization organization = new Organization(); //Create organization
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Super super_villain = new Super(); //Create a super
        super_villain.setName("Test Super Name");
        super_villain.setDescription("Test Super Description");        
        super_villain.setOrganizations(organizations); //add organizations list
        super_villain = superDao.addSuper(super_villain); //save super 

//        superDao.insertOrganization(super_villain); //Create a super_organization (link between super and organization)
        
        Organization organizationFromDao = organizationDao.getOrganizationById(organization.getId()); //get the saved organization from db
        assertEquals(organization, organizationFromDao);
        
        organizationDao.deleteOrganizationById(organization.getId());
        
        organizationFromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(organizationFromDao);
    }
    
} 
        
//        
//        Power power = new Power();
//        power.setName("Test Name");
//        power.setDescription("Test Organization Description");
//        power = powerDao.addPower(power);
//        List<Power> powers = new ArrayList<>();
//        powers.add(power);
