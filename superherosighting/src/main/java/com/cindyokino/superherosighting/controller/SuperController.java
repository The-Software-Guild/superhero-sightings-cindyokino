package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.entity.Organization;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.OrganizationService;
import com.cindyokino.superherosighting.service.PowerService;
import com.cindyokino.superherosighting.service.SuperService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Cindy
 */
@Controller
public class SuperController {
    
   private final OrganizationService organizationService;
   private final PowerService powerService;
   private final SuperService superService;  
   
   public SuperController(OrganizationService organizationService, PowerService powerService, SuperService superService) {
        this.organizationService = organizationService;
        this.powerService = powerService;    
        this.superService = superService;    
    }
    
   
   @GetMapping("supers") //Go to supers html page
    public String displaySupers(Model model) {
        List<Super> supers = superService.getAllSupers();
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("supers", supers);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("super", model.getAttribute("super") != null ? model.getAttribute("super") : new Super());
        
        return "supers"; //returning "supers" means we will need a supers.html file to push our data to
    }
    
    @PostMapping("addSuper")
    public String addSuper(@Valid Super supper, BindingResult result, @RequestParam("superImageToSave") MultipartFile file, HttpServletRequest request, Model model, RedirectAttributes redirect) throws IOException {  
        List<String> powerIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("power_id")).orElse(new String[0])); //if the received parameter is null, create an empty list
        List<String> organizationIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("organization_id")).orElse(new String[0]));
        
        if(result.hasErrors()){  
            List<Super> supers = superService.getAllSupers();
            model.addAttribute("supers", supers); //to fill the listing
            return displaySupers(model);
        }            
        
        superService.addSuper(supper, powerIds, organizationIds, file.getBytes());        
        return "redirect:/supers";
    }
    
    @GetMapping("detailSuper") //Go to detailSuper html page
    public String superDetail(Integer id, Model model) {
        Super supper = superService.getSuperById(id);
        model.addAttribute("super", supper);
        return "detailSuper";
    }
    
    @GetMapping("displayDeleteSuper") //Go to deleteSuper html page for confirmation
    public String displayDeleteSuper(Integer id, Model model) { 
        Super supper = superService.getSuperById(id);
        model.addAttribute("super", supper);
        return "deleteSuper";
    }
    
    @GetMapping("deleteSuper")
    public String deleteSuper(Integer id) {
        superService.deleteSuperById(id);
        return "redirect:/supers";
    }
    
    @GetMapping("editSuper") //Go to editSuper html page
    public String editSuper(Integer id, Model model) {
        Super supper = superService.getSuperById(id);
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        
        if(model.getAttribute("super") != null) {
            ((Super) model.getAttribute("super")).setPowers(supper.getPowers());
            ((Super) model.getAttribute("super")).setOrganizations(supper.getOrganizations());
        }
        model.addAttribute("super", model.getAttribute("super") != null ? model.getAttribute("super") : supper);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "editSuper";
    }
    
    @PostMapping("editSuper")
    public String performEditSuper(@Valid Super supper, BindingResult result, HttpServletRequest request, Model model) {        
        List<String> powerIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("power_id")).orElse(new String[0]));
        List<String> organizationIds = Arrays.asList(Optional.ofNullable(request.getParameterValues("organization_id")).orElse(new String[0]));
        
        if(result.hasErrors()){  
            return editSuper(supper.getId(), model);
        } 
        
        superService.updateSuper(supper, powerIds, organizationIds);

        return "redirect:/detailSuper?id=" + supper.getId();
    }
    
    @GetMapping("supers/{id}/image")
    public void renderSuperImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        Super supper = superService.getSuperById(Integer.parseInt(id));
        
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(supper.getSuperImage());
        IOUtils.copy(is, response.getOutputStream());
    }
    
}
