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
public class SightingDaoDBTest {
    
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
    
    public SightingDaoDBTest() {
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
     * Test of addAndGetSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
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
        
        Sighting sighting = new Sighting(); //Create a sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocation_id(location.getId());
        sighting.setSuper_id(super_villain.getId());        
        sighting = sightingDao.addSighting(sighting); 
        
        Sighting sightingFromDao = sightingDao.getSightingById(sighting.getId()); //get the saved sighting from db
        assertEquals(sighting, sightingFromDao);        
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
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
        
        Sighting sighting1 = new Sighting(); //Create sighting1
        sighting1.setDate(LocalDate.now().minusDays(1)); //Set date to yesterday
        sighting1.setLocation_id(location.getId());
        sighting1.setSuper_id(super_villain.getId());        
        sighting1 = sightingDao.addSighting(sighting1); 
        
        Sighting sighting2 = new Sighting(); //Create sighting2
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation_id(location.getId());
        sighting2.setSuper_id(super_villain.getId());        
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
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
        
        Sighting sighting = new Sighting(); //Create a sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocation_id(location.getId());
        sighting.setSuper_id(super_villain.getId());        
        sighting = sightingDao.addSighting(sighting); 
        
        Sighting sightingFromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, sightingFromDao); //Assert sighting created is equal to sighting saved from db
        
        sighting.setDate(LocalDate.now().minusWeeks(1));
        sightingDao.updateSighting(sighting); 
        
        assertNotEquals(sighting, sightingFromDao); //Assert sighting created is not equal to sighting saved from db, because it was updated
        
        sightingFromDao = sightingDao.getSightingById(sighting.getId());        
        assertEquals(sighting, sightingFromDao); //Assert sighting updated is equal to sighting updated saved from db
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
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
        
        Sighting sighting = new Sighting(); //Create a sighting
        sighting.setDate(LocalDate.now());
        sighting.setLocation_id(location.getId());
        sighting.setSuper_id(super_villain.getId());        
        sighting = sightingDao.addSighting(sighting);
        
        Super savedSuper = superDao.getSuperById(super_villain.getId());        
        assertEquals(savedSuper.getLocations().get(0).getId(), location.getId()); //Assert that the location on the savedSuper's locations list is the power we created
       
        Sighting savedSighting = sightingDao.getSightingById(sighting.getId());
               
        Sighting sightingFromDao = sightingDao.getSightingById(sighting.getId()); //get the saved sighting from db
        assertEquals(sighting, sightingFromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        sightingFromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(sightingFromDao); //Assert that the sighting was deleted
        
        Super superWithoutPower = superDao.getSuperById(super_villain.getId());        
        assertEquals(superWithoutPower.getLocations().size(), 0); //Assert that this super doesn't have a location on it's locations list
    
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByDate() {
        }
    
}