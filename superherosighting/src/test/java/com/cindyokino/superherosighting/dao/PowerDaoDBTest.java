package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Sighting;
import com.cindyokino.superherosighting.entity.Super;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class PowerDaoDBTest {
    
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
    
    
    public PowerDaoDBTest() {
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
     * Test of testAddAndGetPower method, of class PowerDaoDB.
     */
    @Test
    public void testAddAndGetPowerower() {                
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        
        Power powerFromDao = powerDao.getPowerowerById(power.getId());
        
        assertEquals(power, powerFromDao);
    }
    
    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power1 = new Power();
        power1.setName("Test Name1");
        power1.setDescription("Test Description");
        power1 = powerDao.addPower(power1);
        
        Power power2 = new Power();
        power2.setName("Test Name");
        power2.setDescription("Test Description");
        power2 = powerDao.addPower(power2);
        
        List<Power> powers = powerDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(power1));
        assertTrue(powers.contains(power2));
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        
        Power powerFromDao = powerDao.getPowerowerById(power.getId());
        assertEquals(power, powerFromDao);
        
        power.setName("New Name");
        powerDao.updatePower(power);
        
        assertNotEquals(power, powerFromDao);
        
        powerFromDao = powerDao.getPowerowerById(power.getId());
        
        assertEquals(power, powerFromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Description");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);

        Super super_villain = new Super(); //Create a super
        super_villain.setName("Test Super Name");
        super_villain.setDescription("Test Super Description");        
        super_villain.setPowers(powers); //add powers list to super
        superDao.addSuper(super_villain); //save super 
        
        Super savedSuper = superDao.getSuperById(super_villain.getId());
        
        assertEquals(savedSuper.getPowers().get(0).getId(), power.getId()); //Assert that the power on the savedSuper's powers list is the power we created
        
        Power powerFromDao = powerDao.getPowerowerById(power.getId()); //get the saved power from db
        assertEquals(power, powerFromDao);
        
        powerDao.deletePowerById(power.getId());
        
        powerFromDao = powerDao.getPowerowerById(power.getId());
        assertNull(powerFromDao); //Assert that the power was deleted
        
        Super superWithoutPower = superDao.getSuperById(super_villain.getId());
        
        assertEquals(superWithoutPower.getPowers().size(), 0); //Assert that this super doesn't have a power on it's powers list
    }
    
}
