/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.RoadManager;
import domain.Road;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The dao for road.
 * @author Alexander
 */
@Stateless
public class RoadDao extends AbstractDaoFacade<Road> {
    private static final Logger LOGGER
            = Logger.getLogger(RoadDao.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    /**
     * The dao for road.
     */
    public RoadDao() {
        super(Road.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Find all roads in Portugal in the database.
     * @return All found roads.
     */
    public List<Road> findAllInternal() {
        Query q =  this.em.createNamedQuery("Road.findAllInternal");
        return (List<Road>) q.getResultList();
    }
    
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
}
