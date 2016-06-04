/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.RoadDao;
import domain.Road;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Jesse
 */
@Stateless
public class RoadManager {
    @Inject
    private RoadDao roadDao;
        
    /**
     * Finds a Road by the road name.
     * @param name The name of the road.
     * @return The road if found, otherwise null.
     */
    public Road findRoadByName(String name) {
        return this.roadDao.findRoadByName(name);
    }
    
    /**
     * Save the road object.
     * @param road The Road object.
     */
    public void save(Road road) {
        this.roadDao.create(road);
    }
}
