/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Road;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alexander
 */
@Stateless
public class RoadDao extends DaoFacade<Road> {
    @PersistenceContext
    private EntityManager em;

    public RoadDao() {
        super(Road.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
