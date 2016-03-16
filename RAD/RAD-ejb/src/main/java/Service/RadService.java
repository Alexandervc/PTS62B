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

    private final BillDAO bill;
    private final PersonDAO person;

    public RadService() {
        bill = new BillDAOJPAImp();
        person = new PersonDAOJPAImp();
    }

    public void persistBill(Bill object) {
        try {
            bill.create(object);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    public void persistBill(Object object) {
        try {
            //person.create(object);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
