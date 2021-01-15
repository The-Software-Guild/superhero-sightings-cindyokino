package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.LocationDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.LocationService;
import com.cindyokino.superherosighting.service.SuperService;
import java.util.List;
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
public class LocationController {
    
    private final LocationDao locationDao;
    private final LocationService locationService;
    private final SuperService superService;
    
    public LocationController(LocationDao locationDao, LocationService locationService, SuperService superService){
        this.locationDao = locationDao;
        this.locationService = locationService;
        this.superService = superService;
    }
    
    
    @GetMapping("locations") //Go to locations html page
    public String displayLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        
        model.addAttribute("location", model.getAttribute("location") != null ? model.getAttribute("location") : new Location());
        
        return "locations"; //returning "locations" means we will need a locations.html file to push our data to
    }
    
    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, Model model) { 
        if(result.hasErrors()) {
            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
            return displayLocations(model);            
        }
        
        locationService.addLocation(location);        
        return "redirect:/locations";
    }
    
    @GetMapping("detailLocation") //Go to detailLocation html page
    public String detailLocation(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        List<Super> supersByLocation = superService.getSupersByLocation(location);
        model.addAttribute("location", location);
        model.addAttribute("supers", supersByLocation);
        return "detailLocation";
    }
    
    @GetMapping("displayDeleteLocation") //Go to deleteLocation html page for confirmation
    public String displayDeleteLocation(Integer id, Model model) { 
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        return "deleteLocation";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationService.deleteLocationById(id);
        return "redirect:/locations";
    }  
        
     @GetMapping("editLocation") //Go to editLocation html page
        public String editLocation(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        
        model.addAttribute("location", model.getAttribute("location") != null ? model.getAttribute("location") : location);
        
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result, Model model) { 
        if(result.hasErrors()) {
            return editLocation(location.getId(), model);            
        }
        
        locationService.updateLocation(location);
        return "redirect:/detailLocation?id=" + location.getId();
    }
    
}
