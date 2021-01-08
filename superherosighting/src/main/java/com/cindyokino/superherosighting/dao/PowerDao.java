package com.cindyokino.superherosighting.dao;

import com.cindyokino.superherosighting.entity.Power;
import java.util.List;

/**
 *
 * @author Cindy
 */
public interface PowerDao {
    Power getSuperpowerById(int id);
    List<Power> getAllSuperpowers();
    Power addSuperpower(Power superpower);
    void updateSuperpower(Power superpower);
    void deleteSuperpowerById(int id);
}
