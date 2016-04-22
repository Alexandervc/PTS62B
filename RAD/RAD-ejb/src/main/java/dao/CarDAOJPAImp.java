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
 * implemented CarDAO
 * @author Linda
 */
@Stateless
public class CarDAOJPAImp extends AbstractFacade<Car> implements CarDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarDAOJPAImp() {
        super(Car.class);
    }

}
