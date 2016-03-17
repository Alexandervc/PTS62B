/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.BillDAOJPAImp;
import Domain.Bill;
import Service.RadService;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOBill {
        
     
    private RadService service;
    
    @Mock
    private BillDAOJPAImp dao;
        
    public TestDAOBill() {
    }
    
    @Before
    public void setUp() {
        service = new RadService();
        //service.setBillDAO(dao);
        
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
    }
}
