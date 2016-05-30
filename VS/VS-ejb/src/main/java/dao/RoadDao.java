/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Road;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The dao for road.
 * @author Alexander
 */
@Stateless
public class RoadDao extends AbstractDaoFacade<Road> {
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
}
