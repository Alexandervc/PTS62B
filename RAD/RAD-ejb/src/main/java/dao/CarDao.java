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

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarDao() {
        super(Car.class);
    }

}
