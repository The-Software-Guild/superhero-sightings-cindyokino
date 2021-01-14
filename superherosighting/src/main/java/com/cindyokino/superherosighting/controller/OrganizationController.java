package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.OrganizationDao;
import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.OrganizationService;
import com.cindyokino.superherosighting.service.SuperService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final SuperService superService;
    
    public OrganizationController(OrganizationDao organizationDao, OrganizationService organizationService, SuperService superService){
        this.organizationDao = organizationDao;
        this.organizationService = organizationService;
        this.superService = superService;
    }
    
    
    @GetMapping("organizations") //Go to organizations html page
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();          
        model.addAttribute("organizations", organizations);
        model.addAttribute("organization", model.getAttribute("organization") != null ? model.getAttribute("organization") : new Organization());
        
        return "organizations"; //returning "organizations" means we will need a organizations.html file to push our data to
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, Model model) {
        organizationService.addOrganization(organization);
        
        if(result.hasErrors()){  
            List<Organization> organizations = organizationService.getAllOrganizations();
            model.addAttribute("organizations", organizations); //to fill the listing
            return displayOrganizations(model);
        }  
        
        return "redirect:/organizations";
    }
    
    @GetMapping("detailOrganization") //Go to detailOrganization html page
    public String detailOrganization(Integer id, Model model) {
        Organization organization = organizationService.getOrganizationById(id);
        List<Super> membersByOrganization = superService.getSupersByOrganization(organization);
        model.addAttribute("organization", organization);
        model.addAttribute("members", membersByOrganization);
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
    public String performEditOrganization(@Valid Organization organization, BindingResult result, Model model) { 
        organizationService.updateOrganization(organization);
        
        if(result.hasErrors()){  
            List<Organization> organizations = organizationService.getAllOrganizations();
            model.addAttribute("organizations", organizations); //to fill the listing
            return "editOrganization";
        } 
        
        return "redirect:/detailOrganization?id=" + organization.getId();
    }
    
}
