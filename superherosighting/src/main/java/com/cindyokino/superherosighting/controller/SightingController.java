package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.SightingDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Sighting;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.LocationService;
import com.cindyokino.superherosighting.service.SightingService;
import com.cindyokino.superherosighting.service.SuperService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
public class SightingController {
    
    private final SightingDao sightingDao;
    private final SuperService superService;
    private final LocationService locationService;
    private final SightingService sightingService;
    
    public SightingController(SightingDao sightingDao, SuperService superService, LocationService locationService, SightingService sightingService){
        this.sightingDao = sightingDao;
        this.superService = superService;
        this.locationService = locationService;
        this.sightingService = sightingService;
    }
    
    
    @GetMapping("sightings") //Go to sightings html page
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();
        List<Super> supers = superService.getAllSupers();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations);
        
        return "sightings"; //returning "sightings" means we will need a sightings.html file to push our data to
    }
    
    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {
        String superId = request.getParameter("super_id");
        String locationId = request.getParameter("location_id");        
        sighting.setSuperId(Integer.parseInt(superId));
        sighting.setLocationId(Integer.parseInt(locationId));        
        sightingDao.addSighting(sighting);        
        return "redirect:/sightings";
    }
    
    @GetMapping("detailSighting") //Go to detailSighting html page
    public String detailSighting(Integer id, Model model) {
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "detailSighting";
    }
    
    @GetMapping("displayDeleteSighting") //Go to deleteSighting html page for confirmation
    public String displayDeleteSighting(Integer id, Model model) { 
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "deleteSighting";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingService.deleteSightingById(id);
        return "redirect:/sightings";
    }  
        
     @GetMapping("editSighting") //Go to editSighting html page
        public String editSighting(Integer id, Model model) {
        List<Super> supers = superService.getAllSupers();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations); 
        model.addAttribute("currentDate", LocalDate.now().toString());
            
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request) { 
        String superId = request.getParameter("super_id");
        String locationId = request.getParameter("location_id");        
        sighting.setSuperId(Integer.parseInt(superId));
        sighting.setLocationId(Integer.parseInt(locationId));  
        sightingService.updateSighting(sighting);
        return "redirect:/detailSighting?id=" + sighting.getId();
    }
    
//  INDEX PAGE - DISPLAY 10 MOST RECENT SIGHTINGS
    @GetMapping("/") //Go to index html page
    public String recentSightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();      
        
        List<Sighting> recentSightings = sightings.stream()
                .sorted(Comparator.comparing(Sighting::getDate).reversed()) //order by date from most recent to oldest
                .limit(10) //get the 10 first sightings
                .collect(Collectors.toList());        

        model.addAttribute("sightings", recentSightings);
        
        return "index.html"; //returning "sightings" means we will need a sightings.html file to push our data to
    }
    
}
