/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.BillDAOJPAImp;
import Domain.Bill;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Linda
 */
public class TestDAOBill {
    
    private BillDAOJPAImp billDAO;
    private EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("RADpu");
    private EntityManager em;
    
    public TestDAOBill() {
        em = emf.createEntityManager();
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void addBill(){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        Bill bill = new Bill(10.25);
        billDAO = new BillDAOJPAImp(em);
        billDAO.create(bill);
    }
}
