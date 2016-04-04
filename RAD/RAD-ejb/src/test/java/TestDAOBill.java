/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import business.BillManager;
import business.RoadUsage;
import domain.Bill;
import domain.Person;
import domain.RoadType;
import java.util.ArrayList;
import java.util.List;
import service.RadService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service.IRoadUsage;

/**
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAOBill {
    RadService service;
        
    @Mock
    BillManager billManager;
    
    Person person1;
    Bill bill1;
    
    public TestDAOBill() {       
    }
    
    @Before
    public void setUp() {
        service = new RadService();
        service.setBillManager(billManager);
                
        person1 = new Person("Linda", "van Engelen", "LMJC", "Sibeliuslaan", "83",
                "5654CV", "Eindhoven", "Nederland");
        
        List<IRoadUsage> roadUsages = new ArrayList<IRoadUsage>();
        roadUsages.add(new RoadUsage("Rachelsmolen", RoadType.C, 5.00));
        
        bill1 = new Bill(person1, roadUsages, 10.35);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddBill(){
       service.addBill(bill1);
        verify(billManager, Mockito.times(1)).createBill(bill1);
    }
}
