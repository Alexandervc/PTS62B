/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junitests;

import business.ServerStatusManager;
import common.domain.ServerStatus;
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
    private ServerStatusManager manager;
        
    /**
     * Tests the retrieve application status method on the LMS server.
     * 
     * This requires the C:/glassfish4/glassfish/bin 
     * (or C:/payara41/glassfish/bin) folder to be added to the PATH variable of
     * the system, as well as a password file on the PASSWORD_FILE location 
     * containing the following parameters:
     * 
     * AS_ADMIN_PASSWORD=admin
     * AS_ADMIN_ADMINPASSWORD=admin
     * AS_ADMIN_USERPASSWORD=admin
     * AS_ADMIN_MASTERPASSWORD=admin
     * 
     * @throws IOException Thrown if the asadmin file was not found in 
     * C:/Proftaak.
     */
    @Test
    public void retrieveApplicationStatusTest() 
            throws IOException, InterruptedException {
        common.domain.System system = new common.domain.System();
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