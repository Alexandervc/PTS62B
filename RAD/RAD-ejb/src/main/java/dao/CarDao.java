/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Car;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implemented CarDao.
 * @author Linda.
 */
@Stateless
public class CarDao extends AbstractFacade<Car> implements Serializable {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Constructor.
     */
    public CarDao() {
        super(Car.class);
    }

    /**
     * Getter EntityManager.
     * @return em type EntityManager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
}
