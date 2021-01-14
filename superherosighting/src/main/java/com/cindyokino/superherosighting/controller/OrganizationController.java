package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.OrganizationDao;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.service.OrganizationService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Cindy
 */
@Controller
public class OrganizationController {
    
    private final OrganizationDao organizationDao;
    private final OrganizationService organizationService;
    
    public OrganizationController(OrganizationDao organizationDao, OrganizationService organizationService){
        this.organizationDao = organizationDao;
        this.organizationService = organizationService;
    }
    
    
    @GetMapping("organizations") //Go to organizations html page
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations"; //returning "organizations" means we will need a organizations.html file to push our data to
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization) {
        organizationService.addOrganization(organization);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("detailOrganization") //Go to detailOrganization html page
    public String detailOrganization(Integer id, Model model) {
        Organization organization = organizationService.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "detailOrganization";
    }
    
    @GetMapping("displayDeleteOrganization") //Go to deleteOrganization html page for confirmation
    public String displayDeleteOrganization(Integer id, Model model) { 
        Organization organization = organizationService.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "deleteOrganization";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationService.deleteOrganizationById(id);
        return "redirect:/organizations";
    }  
        
     @GetMapping("editOrganization") //Go to editOrganization html page
        public String editOrganization(Integer id, Model model) {
        Organization organization = organizationService.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization) { 
        organizationService.updateOrganization(organization);
        return "redirect:/detailOrganization?id=" + organization.getId();
    }
    
}
