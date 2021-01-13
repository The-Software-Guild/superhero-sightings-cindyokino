package com.cindyokino.superherosighting.controller;

import com.cindyokino.superherosighting.dao.PowerDao;
import com.cindyokino.superherosighting.entity.Power;
import com.cindyokino.superherosighting.service.PowerService;
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
public class PowerController {
    
    private final PowerDao powerDao;
    private final PowerService powerService;
    
    public PowerController(PowerDao powerDao, PowerService powerService){
        this.powerDao = powerDao;
        this.powerService = powerService;
    }
    
    
    @GetMapping("powers") //Go to powers html page
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers"; //returning "powers" means we will need a powers.html file to push our data to
    }
    
    @PostMapping("addPower")
    public String addPower(String name, String description) {
        Power power = new Power();
        power.setName(name);
        power.setDescription(description);
        powerService.addPower(power);
        
        return "redirect:/powers";
    }
    
    @GetMapping("detailPower") //Go to detailPower html page
    public String detailPower(Integer id, Model model) {
        Power power = powerService.getPowerById(id);
        model.addAttribute("power", power);
        return "detailPower";
    }
    
    @GetMapping("displayDeletePower") //Go to deletePower html page for confirmation
    public String displayDeletePower(Integer id, Model model) { 
        Power power = powerService.getPowerById(id);
        model.addAttribute("power", power);
        return "deletePower";
    }
    
    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        powerService.deletePowerById(id);
        return "redirect:/powers";
    }  
        
     @GetMapping("editPower") //Go to editPower html page
        public String editPower(Integer id, Model model) {
        Power power = powerService.getPowerById(id);
        model.addAttribute("power", power);
        return "editPower";
    }
    
    @PostMapping("editPower")
    public String performEditPower(Power power) { 
        powerService.updatePower(power);
        return "redirect:/detailPower?id=" + power.getId();
    }
    
}
