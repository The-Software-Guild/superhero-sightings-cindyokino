package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.LocationService;
import com.cindyokino.superherosighting.service.OrganizationService;
import com.cindyokino.superherosighting.service.PowerService;
import com.cindyokino.superherosighting.service.SightingService;
import com.cindyokino.superherosighting.service.SuperService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Cindy
 */
@Controller
public class SuperController {
    
   private final LocationService locationService;
   private final OrganizationService organizationService;
   private final PowerService powerService;
   private final SightingService sightingService;
   private final SuperService superService;  
   
   public SuperController(LocationService locationService, OrganizationService organizationService, PowerService powerService, SightingService sightingService, SuperService superService) {
        this.locationService = locationService;
        this.organizationService = organizationService;
        this.powerService = powerService;    
        this.sightingService = sightingService;    
        this.superService = superService;    
    }
    
   
   @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Super> supers = superService.getAllSupers();
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("supers", supers);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "supers"; //returning "supers" means we will need a supers.html file to push our data to
    }
    
    @PostMapping("addSuper")
    public String addSuper(Super supper, HttpServletRequest request) {        
        List<String> powerIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("power_id")).orElse(new String[0]));
        List<String> organizationIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("organization_id")).orElse(new String[0]));
      
        superService.addSuper(supper, powerIds, organizationIds);
        
        return "redirect:/supers";
    }
    
    @GetMapping("detailSuper")
    public String superDetail(Integer id, Model model) {
        Super supper = superService.getSuperById(id);
        model.addAttribute("super", supper);
        return "detailSuper";
    }
    
    @GetMapping("displayDeleteSuper") //Go to deleteSuper html page for confirmation
    public String displayDeleteSuper(Integer id, Model model) { 
        Super supper = superService.getSuperById(id);
        model.addAttribute("super", supper);
        return "deleteSuper"; //returning "deleteSuper" means we will need a deleteSuper.html file to push our data to
    }
    
    @GetMapping("deleteSuper")
    public String deleteSuper(Integer id) {
        superService.deleteSuperById(id);
        return "redirect:/supers";
    }
    
    @GetMapping("editSuper")
    public String editSuper(Integer id, Model model) {
        Super supper = superService.getSuperById(id);
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("super", supper);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "editSuper";
    }
    
    @PostMapping("editSuper")
    public String performEditSuper(Super supper, HttpServletRequest request) {        
        List<String> powerIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("power_id")).orElse(new String[0]));
        List<String> organizationIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("organization_id")).orElse(new String[0]));
        
        superService.updateSuper(supper, powerIds, organizationIds);

        return "redirect:/detailSuper?id=" + supper.getId();
//        return new ModelAndView("redirect:/detailSuper/${supper.getId()}", supper.getId());
    }
    
}
