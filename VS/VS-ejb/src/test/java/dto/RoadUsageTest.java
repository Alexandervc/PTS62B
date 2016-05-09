/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import domain.RoadType;

/**
 * Test for RoadUsage.
 * @author Alexander
 */
public class RoadUsageTest {
    private RoadUsage roadUsage;
    
    @Before
    public void setUp() {
        this.roadUsage = new RoadUsage("roadName", RoadType.A, 0.0);
    }
    
    @Test
    public void addMeter0Shouldadd0() {
        Double expected = 0.0;
        this.roadUsage.addMeter(0.0);
        assertEquals(expected, this.roadUsage.getKm());
    }
    
    @Test
    public void addMeter500And1000ShouldAddOneAndHalf() {
        Double expected = 1.0;
        this.roadUsage.addMeter(1000.0);
        assertEquals(expected, this.roadUsage.getKm());
        
        expected = 1.5;
        this.roadUsage.addMeter(500.0);
        assertEquals(expected, this.roadUsage.getKm());
    }
}
