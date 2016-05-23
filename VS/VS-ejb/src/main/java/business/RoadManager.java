/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import dao.RoadDao;
import domain.Road;
import domain.RoadType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jesse
 */
@Stateless
public class RoadManager {
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
            // Do nothing.
        }
        
        return road;
    }
    
    public void save(Road road) {
        this.em.persist(road);
    }
}
