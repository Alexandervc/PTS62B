/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.RoadType;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for RoadUsage.
 * @author Alexander
 */
public class RoadUsageTest {
    private RoadUsage roadUsage;
    
    @Before
    public void setUp() {
        roadUsage = new RoadUsage("roadName", RoadType.A, 0.0);
    }
    
    @Test
    public void addMeter0Shouldadd0() {
        Double expected = 0.0;
        roadUsage.addMeter(0.0);
        assertEquals(expected, roadUsage.getKm());
    }
    
    @Test
    public void addMeter500And1000ShouldAddOneAndHalf() {
        Double expected = 1.0;
        roadUsage.addMeter(1000.0);
        assertEquals(expected, roadUsage.getKm());
        
        expected = 1.5;
        roadUsage.addMeter(500.0);
        assertEquals(expected, roadUsage.getKm());
    }
}
