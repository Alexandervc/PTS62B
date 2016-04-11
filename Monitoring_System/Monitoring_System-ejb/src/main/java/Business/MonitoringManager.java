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
import data.testinject;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.annotation.PostConstruct;
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
    
    @Inject testinject inject;
    
    private final static Logger LOGGER = Logger.getLogger(MonitoringManager.class.getName()); 
    
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
    public List<System> getSystems() {
        inject.toString();
        if(inject == null) {
            LOGGER.log(Level.INFO, "inject is null");
        }
        if(systemDao == null) {
            if(testDao == null) {
                LOGGER.log(Level.INFO, "testDao is null");
            }
            LOGGER.log(Level.INFO, "systemDao is null");
            return new ArrayList<>();
        }
        return this.systemDao.getSystems();
    }
    
    /**
     * Initializes the monitoring manager by creating a clientmap
     * and loading in the RMI servers.
     */
    @PostConstruct	
    public void init() {
        this.clientMap = new HashMap<>();
    }

    /**
     * Generates the status of the server.
     * @param system The system object where the status will be generated for.
     * @return A list of the three types of test.
     */
    public final List<Test> generateServerStatus(System system) {
        List<Test> tests = new ArrayList<>();
        
        // Retrieve the server status.
        Test serverStatusTest = this.retrieveServerStatus(system);
        tests.add(serverStatusTest);
        
        // Retrieve the result of the functional tests.
        Test functionalTest = this.retrieveFunctionalTests(system);
        tests.add(functionalTest);
        
        // Retrieve the result of the endpoint test.
        Test endpointTest = this.retrieveEndpointTest(system);
        tests.add(endpointTest);
        
        return tests;
    }
    
    /**
     * Retrieves the server status and maps it to a Test object.
     * @param system The system to retrieve the status from.
     * @return A test object with the result of the test
     */
    private Test retrieveServerStatus(System system) {
        List<Test> tests = new ArrayList<>();
        Map<String, ServerStatus> applicationStatus = null;
        
        // Try to get the status of all deployed applications of the system.
        try {
            applicationStatus = 
                    serverStatusManager.retrieveApplicationStatus(system);
        } catch (IOException | InterruptedException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        // If the application status was retrieved, iterate through the results
        // and create a test object.
        if (applicationStatus != null) {
            for (Map.Entry<String, ServerStatus> entry 
                    : applicationStatus.entrySet())
            {
                // Convert util.Date to sql.Date.
                java.util.Date currentDate = new java.util.Date();                
                
                // Create a new test object to return.
                Test test = new Test(
                        TestType.STATUS, 
                        new Timestamp(currentDate.getTime()), 
                        entry.getValue() == ServerStatus.ONLINE);

                // Add the test to the result list.
                tests.add(test);
            }
        }
        
        // Check if at least one of the applications is offline.
        for (Test test : tests) {
            // If the test result is false (i.e. the application is offline, the
            // test fails.
            if (!test.getResult()) {
                return test;
            }
        }
        
        // If the server status was retrieved, get the first test object.
        // This can be expanded by getting all the tests. The application
        // currently expects one deployed application per server (system).
        return tests.get(0);
    }
    
    /**
     * Executes the remote functional tests and gets the result.
     * @param system The system to retrieve the status from.
     * @return A test object with the result of the test.
     */
    private Test retrieveFunctionalTests(System system) {
        // TODO: create the functional test.
        
        // Create the test object which is returned.
        Test test = new Test(
                TestType.FUNCTIONAL,
                new Timestamp(java.lang.System.currentTimeMillis()),
                false);
        
        return test;
    }
    
    /**
     * Executes the endpoint tests and gets the result.
     * @param system The system to retrieve the status from.
     * @return A test object with the result of the test.
     */
    private Test retrieveEndpointTest(System system) {
        // TODO: create the endpoint test.
        
        // Create the test object which is returned.
        Test test = new Test(
                TestType.FUNCTIONAL,
                new Timestamp(java.lang.System.currentTimeMillis()),
                false);
        
        return test;
    }
}