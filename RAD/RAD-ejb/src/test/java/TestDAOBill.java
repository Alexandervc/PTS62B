/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.BillDAOJPAImp;
import Domain.Bill;
import Service.RadService;
import javax.ejb.EJB;
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
    @EJB  
    private RadService service;
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("RADpu");
    
    public TestDAOBill() {
        
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
        Bill b = new Bill(20.59);
        service.persistBill(b);
    }
}
