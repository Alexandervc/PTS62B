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
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Provides the unit tests for the ServerStatusManager.
 * @author jesblo
 */
@RunWith(CdiRunner.class)
public class ServerStatusManagerTest {
    
    @Inject
    private ServerStatusManager manager;
    
    /**
     * The IP address of the system to test.
     */
    public static final String SYSTEM_IP = "192.168.24.70";
        
    /**
     * <p>Tests the retrieve application status method on the LMS server.</p>
     * 
     * <p>This requires the C:/glassfish4/glassfish/bin 
     * (or C:/payara41/glassfish/bin) folder to be added to the PATH variable of
     * the system, as well as a password file on the PASSWORD_FILE location 
     * containing the following parameters:</p>
     * 
     * <p>AS_ADMIN_PASSWORD=admin
     * AS_ADMIN_ADMINPASSWORD=admin
     * AS_ADMIN_USERPASSWORD=admin
     * AS_ADMIN_MASTERPASSWORD=admin</p>
     * 
     * @throws IOException Thrown if the asadmin file was not found in 
     * C:/Proftaak.
     */
    @Test
    public void retrieveApplicationStatusTest() 
            throws IOException {
        common.domain.System system = new common.domain.System();
        system.setIp(SYSTEM_IP);
        
        // Retrieve the application status of the system.
        Map<String, ServerStatus> applicationStatus
                = this.manager.retrieveApplicationStatus(system);
        
        // Check if the status of an application was retrieved.
        assertTrue(!applicationStatus.isEmpty());
    }
}