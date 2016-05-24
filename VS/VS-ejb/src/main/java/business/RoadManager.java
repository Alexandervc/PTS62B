/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.RoadDao;
import domain.Road;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Jesse
 */
@Stateless
public class RoadManager {
    private static final Logger LOGGER
            = Logger.getLogger(RoadManager.class.getName());
    @Inject
    private RoadDao roadDao;
    
    @PersistenceContext
    private EntityManager em;
        
    /**
     * Finds a Road by the road name.
     * @param name The name of the road.
     * @return The road if found, otherwise null.
     */
    public Road findRoadByName(String name) {
        Query q = this.em.createNamedQuery("Road.findByName");
        q.setParameter("name", name);
        
        Road road = null;
        
        try {
            road = (Road) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            // Do nothing. Null will be returned.
            LOGGER.log(
                    Level.FINE,
                    "Road: '" + name + "' does not yet exist. " + ex);
        }
        
        return road;
    }
    
    /**
     * Save the road object.
     * @param road The Road object.
     */
    public void save(Road road) {
        this.em.persist(road);
    }
}
