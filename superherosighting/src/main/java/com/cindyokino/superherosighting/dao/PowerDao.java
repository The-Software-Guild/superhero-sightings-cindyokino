package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Power;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface PowerDao {
    Power getPowerById(int id);
    List<Power> getAllPowers();
    Power addPower(Power power);
    void updatePower(Power power);
    void deletePowerById(int id);
}
