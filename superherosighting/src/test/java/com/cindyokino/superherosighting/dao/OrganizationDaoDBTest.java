package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Super;
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
    OrganizationDao organizationDao;    
                
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
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
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

        Super heroVillain = new Super(); //Create a super
        heroVillain.setName("Test Super Name");
        heroVillain.setDescription("Test Super Description");        
        heroVillain.setOrganizations(organizations); //add organizations list
        superDao.addSuper(heroVillain); //save super 
        
        Super savedSuper = superDao.getSuperById(heroVillain.getId());
        
        assertEquals(savedSuper.getOrganizations().get(0).getId(), organization.getId());
        
        Organization organizationFromDao = organizationDao.getOrganizationById(organization.getId()); //get the saved organization from db
        assertEquals(organization, organizationFromDao); //Assert that the organization created is the same as the organization get from the db
        
        organizationDao.deleteOrganizationById(organization.getId());
        
        organizationFromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(organizationFromDao); //Assert that the organization was deleted
        
        Super superWithoutOrganization = superDao.getSuperById(heroVillain.getId());
        
        assertEquals(superWithoutOrganization.getOrganizations().size(), 0); //Assert that this super doesn't have an organization        
    }
    
} 
