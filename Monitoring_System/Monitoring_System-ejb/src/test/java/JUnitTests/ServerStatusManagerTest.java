/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnitTests;

import business.ServerStatusManager;
import common.Domain.ServerStatus;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jesblo
 */
@RunWith(CdiRunner.class)
public class ServerStatusManagerTest {
    
    @Inject
    ServerStatusManager manager;
        
    @Test
    public void retrieveApplicationStatusTest() 
            throws IOException, InterruptedException {
        common.Domain.System system = new common.Domain.System();
        system.setIp("192.168.24.70");
        
        // Retrieve the application status of the system.
        Map<String, ServerStatus> applicationStatus
                = manager.retrieveApplicationStatus(system);
        
        // Iterate through the applications and print the status.
        for (Map.Entry<String, ServerStatus> entry : applicationStatus.entrySet())
        {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
        // Check if the status of an application was retrieved.
        assertTrue(!applicationStatus.isEmpty());
    }
}