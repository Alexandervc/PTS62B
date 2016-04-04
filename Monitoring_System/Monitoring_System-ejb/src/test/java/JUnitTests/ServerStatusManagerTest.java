/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnitTests;

import business.ServerStatusManager;
import common.domain.ServerStatus;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author jesblo
 */
@RunWith(CdiRunner.class)
public class ServerStatusManagerTest {
    
    @Inject
    ServerStatusManager manager;
    
    public ServerStatusManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void retrieveApplicationStatusTest() 
            throws IOException, InterruptedException {
        common.domain.System system = new common.domain.System();
        system.setIp("192.168.24.70");
        
        Map<String, ServerStatus> applicationStatus
                = manager.retrieveApplicationStatus(system);
        
        assertTrue(!applicationStatus.isEmpty());
    }
}