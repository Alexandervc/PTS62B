/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CarPosition;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The dao for carPosition.
 * @author Alexander
 */
@Stateless
public class CarPositionDao extends AbstractDaoFacade<CarPosition> {
    @PersistenceContext
    private EntityManager em;

    /**
     * The dao for carPosition.
     */
    public CarPositionDao() {
        super(CarPosition.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    /**
     * Get the carpositions between the given dates for the given cartracker.
     * @param begin The begin date of the period to get the carpositions for.
     * @param end The end date of the period to get the carpositions for.
     * @param cartrackerId The cartracker to get the carpositions for.
     * @return A list of carpositions.
     */
    public List<CarPosition> getPositionsBetween(Date begin, Date end, 
            String cartrackerId) {
        Query q = this.em.createNamedQuery("CarPosition.getPositionsBetween");
        // TODO weer terug zetten
        //q.setParameter("begin", begin);
        //q.setParameter("end", end);
        q.setParameter("cartrackerId", cartrackerId);
        return q.getResultList();
    }
}
