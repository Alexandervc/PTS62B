/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.BillDAO;
import DAO.BillDAOJPAImp;
import DAO.PersonDAO;
import DAO.PersonDAOJPAImp;
import Domain.Bill;
import Domain.Person;
import Domain.RoadType;
import Domain.RoadUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    
    @Inject //@Named("billRepo")
    private BillDAO billDAO;
    @Inject //@Named("personRepo")
    private PersonDAO personDAO;

    @PostConstruct
    public void init() {
        Bill b = new Bill();
        RoadUsage road = new RoadUsage(1L, "AutoWeg", RoadType.A, 25.36);
        List<RoadUsage> roads = new ArrayList<>();
        roads.add(road);
        b.setRoadUsage(roads);
        persistBill(b);

        Person p = new Person("Test");
        persistPerson(p);
    }

    public RadService() {
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
