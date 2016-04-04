/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import common.domain.ServerStatus;
import common.domain.System;
import common.domain.Test;
import common.domain.TestType;
import data.RMI_Client;
import data.SystemDao;
import data.TestDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class MonitoringManager {

    private Map<String,RMI_Client> clientMap;
    
    @Inject
    private ServerStatusManager serverStatusManager;
    
    @Inject
    private SystemDao systemDao;
    
    @Inject
    private TestDao testDao;
    
    /**
     * Instantiates the MonitoringManager class.
     */
    public MonitoringManager() {
        // Empty constructor for Sonarqube.
    }
    
    /**
     * Retrieves a list of Systems that are currently part of the RRA
     * application.
     * @return A list of servers.
     */
    public final List<System> getSystems() {
        return this.systemDao.getSystems();
    }
    
    /**
     * Generates the status of the server
     * @param system The system object where the status will be generated for.
     * @return A list of the three types of test.
     */
    public final List<Test> generateServerStatus(System system) {
        List<Test> tests = new ArrayList<>();
        
        // Retrieve the server status.
        List<Test> serverStatusTest = this.retrieveServerStatus(system);
        
        // If the server status was retrieved, get the first test object.
        // This can be expanded by getting all the tests. The application
        // currently expects one deployed application per server (system).
        if (serverStatusTest.size() > 0) {
            tests.add(serverStatusTest.get(0));
        }
        
        // TODO: functional and endpoint tests.
        
        return tests;
    }
    
    /**
     * Retrieves the server status and maps it to a Test object.
     * @param system The system to retrieve the status from.
     * @return 
     */
    private List<Test> retrieveServerStatus(System system) {
        List<Test> tests = new ArrayList<>();
        Map<String, ServerStatus> applicationStatus = null;
        
        // Try to get the status of all deployed applications of the system.
        try {
            applicationStatus = 
                    serverStatusManager.retrieveApplicationStatus(system);
        } catch (IOException ex) {
            // TODO: Add logging.
            Logger.getLogger(MonitoringManager.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            // TODO: Add logging.
            Logger.getLogger(MonitoringManager.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        // If the application status was retrieved, iterate through the results
        // and create a test object.
        if (applicationStatus != null) {
            for (Map.Entry<String, ServerStatus> entry 
                    : applicationStatus.entrySet())
            {
                // Convert util.Date to sql.Date.
                java.util.Date currentUtilDate = new java.util.Date();
                java.sql.Date currentSqlDate 
                        = new java.sql.Date(currentUtilDate.getTime());
                
                // Create a new test object to return.
                Test test = new Test(
                        TestType.STATUS, 
                        currentSqlDate, 
                        entry.getValue() == ServerStatus.ONLINE);

                // Add the test to the result list.
                tests.add(test);
            }
        }
        
        return tests;
    }
}