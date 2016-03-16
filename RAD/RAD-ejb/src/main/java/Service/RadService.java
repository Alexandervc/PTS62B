/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.BillDAOJPAImp;
import DAO.PersonDAOJPAImp;
import Domain.Bill;
import Domain.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Linda
 */
@Singleton
@LocalBean
@Startup
public class RadService {

    @PersistenceContext(unitName = "RADpu")
    private EntityManager em;
    private final BillDAOJPAImp billDAO;
    private final PersonDAOJPAImp personDAO;

    @PostConstruct
    public void init() {
        Bill b = new Bill(20.35);
        persistBill(b);
        
        Person p = new Person("Linda");
        persistPerson(p);
    }
    
    public RadService() {
        billDAO = new BillDAOJPAImp();
        personDAO = new PersonDAOJPAImp();
    }

    public void persistBill(Bill b) {
        try {
            billDAO.setEntityManager(em);
            billDAO.create(b);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public void persistPerson(Person p) {
        try {
            personDAO.setEntityManager(em);
            personDAO.create(p);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
