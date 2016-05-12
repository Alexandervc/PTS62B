/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.RateManager;
import domain.RoadType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import service.RadService;

/**
 * Test Rate Dao.
 *
 * @author Linda.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestDAORate {

    private RadService service;

    @Mock
    private RateManager rateManager;

    private static final double RATE = 9.76;
    private static final RoadType TYPE = RoadType.A;

    /**
     * Constructor.
     */
    public TestDAORate() {
        // Empty for start up test
    }

    /**
     * Set up at beginning test.
     */
    @Before
    public void setUp() {
        this.service = new RadService();
        this.service.setRateManager(this.rateManager);
    }

    @Test
    public void testAddRate() {
        this.service.addRate(RATE, TYPE);
        verify(this.rateManager, Mockito.times(1)).createRate(RATE, TYPE);
    }
}
