package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.LocationDao;
import com.cindyokino.superherosighting.entity.Location;
import com.cindyokino.superherosighting.entity.Super;
import com.cindyokino.superherosighting.service.LocationService;
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
public class LocationController {
    
    private final LocationDao locationDao;
    private final LocationService locationService;
    
    public LocationController(LocationDao locationDao, LocationService locationService){
        this.locationDao = locationDao;
        this.locationService = locationService;
    }
    
    
    @GetMapping("locations") //Go to locations html page
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations"; //returning "locations" means we will need a locations.html file to push our data to
    }
    
    @PostMapping("addLocation")
    public String addLocation(Location location) {        
        locationService.addLocation(location);
        
        return "redirect:/locations";
    }
    
    @GetMapping("detailLocation") //Go to detailLocation html page
    public String detailLocation(Integer id, Model model) {
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);
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
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(Location location) { 
        locationService.updateLocation(location);
        return "redirect:/detailLocation?id=" + location.getId();
    }
    
}
