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
public class LocationDaoDBTest {
    
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
    
    
    public LocationDaoDBTest() {
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
     * Test of getAndAddLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {        
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */    
    @Test
    public void testGetAllLocations() {
        Location location1 = new Location();
        location1.setName("Test Location Name1");
        location1.setDescription("Test Location Description1");
        location1.setAddress("Test Location Address1");
        location1.setLatitude(45.501690);
        location1.setLongitude(-73.567253);
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("Test Location Name2");
        location2.setDescription("Test Location Description2");
        location2.setAddress("Test Location Address2");
        location2.setLatitude(45.604290);
        location2.setLongitude(-73.453583);
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }
    
    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */  
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setName("New Location Name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {  
        Location location = new Location(); //Create a location
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(45.501690);
        location.setLongitude(-73.567253);
        location = locationDao.addLocation(location);
        List<Location> locations = new ArrayList<>();
        locations.add(location);

        Super super_villain = new Super(); //Create a super
        super_villain.setName("Test Super Name");
        super_villain.setDescription("Test Super Description");        
        super_villain.setLocations(locations); //add locations list
        super_villain = superDao.addSuper(super_villain); //save super
        
        Sighting sighting = new Sighting(); //Create a sighting (link between super and location)
        sighting.setDate(LocalDate.now());
        sighting.setLocation_id(location.getId());
        sighting.setSuper_id(super_villain.getId());        
        sighting = sightingDao.addSighting(sighting);        
        
        Location fromDao = locationDao.getLocationById(location.getId()); //get the saved location from db
        assertEquals(location, fromDao);
        
        Sighting sightingFromDao = sightingDao.getSightingById(sighting.getId()); //get the saved sighting from db
        assertEquals(sighting, sightingFromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
        assertNull(sightingDao.getSightingById(sighting.getId()));
    }
    
}
