/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao1;

import domain1.Bill;
import domain1.Car;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Linda
 */
@Stateless
public class CarDAOJPAImp extends AbstractFacade<Car> implements CarDAO, Serializable {

    @PersistenceContext(unitName = "RADpu")
    //@PersistenceContext(unitName = "DEVdbRADpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarDAOJPAImp() {
        super(Car.class);
    }

}
