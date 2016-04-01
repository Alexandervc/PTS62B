/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.CarPosition;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alexander
 */
@Stateless
public class CarPositionDao extends DaoFacade<CarPosition> {
    @PersistenceContext
    private EntityManager em;

    public CarPositionDao() {
        super(CarPosition.class);
        this.em = Persistence.createEntityManagerFactory("carTrackingPU").createEntityManager();
    }
    
    @PostConstruct
    public void start() {
        System.out.println("Post construct CarPositionDao");
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<CarPosition> getPositionsBetween(Date begin, Date end, Long cartrackerId) {
        Query q = em.createNamedQuery("CarPosition.getPositionsBetween");
        q.setParameter("begin", begin);
        q.setParameter("end", end);
        q.setParameter("cartrackerId", cartrackerId);
        return q.getResultList();
    }
}
