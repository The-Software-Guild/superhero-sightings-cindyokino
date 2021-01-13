package com.cindyokino.superherosighting.service;

import com.cindyokino.superherosighting.dao.PowerDao;
import com.cindyokino.superherosighting.entity.Power;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cindy
 */
@Service
public class PowerService {
        
    private final PowerDao powerDao;
    
    public PowerService(PowerDao powerDao) {
        this.powerDao = powerDao;
    }
    
    
    public Power getPowerById(int id) {
        Power power = powerDao.getPowerById(id);
        return power;
    }
    
    public List<Power> getAllPowers() {
        List<Power> powers = powerDao.getAllPowers();
        return powers;
    }
    
    public Power addPower(Power power) {
        Power newPower = powerDao.addPower(power);
        return newPower;
    }
    
    public void updatePower(Power power) {
        powerDao.updatePower(power);
    }
    
    public void deletePowerById(int id) {
        powerDao.deletePowerById(id);
    }
    
}
