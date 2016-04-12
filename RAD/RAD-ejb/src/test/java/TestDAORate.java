/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import business1.RateManager;
import domain1.RoadType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service1.RadService;

/**
 *
 * @author Linda
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAORate {
    
    RadService service;
    
    @Mock
    RateManager rateManager;
    
    public TestDAORate() {
    }
    
    @Before
    public void setUp() {
        service = new RadService();
        service.setRateManager(rateManager);
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
    public void testAddRate(){
        double rate = 9.76;
        RoadType type = RoadType.E;
        service.addRate(rate, type);
        verify(rateManager, Mockito.times(1)).createRate(rate, type);
    }
}
