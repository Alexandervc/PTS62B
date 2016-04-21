/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import common.domain.ServerStatus;
import common.domain.System;
import common.domain.Test;
import common.domain.TestType;
import data.SystemDao;
import data.TestDao;
import data.jms.CheckRequestSender;

/**
 *
 * @author Edwin.
 */
@Stateless
public class MonitoringManager {
    
    private static final Logger LOGGER = Logger
            .getLogger(MonitoringManager.class.getName()); 

    @EJB
    private CheckRequestSender checkRequestSender;
    
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
    public List<System> getSystems() {
        return this.systemDao.getSystems();
    }   

    /**
     * Generates the status of the server.
     * @param system The system object where the status will be generated for.
     */
    public final void generateServerStatus(System system) {
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
        
        for(Test test :tests) {
            this.testDao.create(test);
        }
        this.systemDao.edit(tests);
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
                    this.serverStatusManager.retrieveApplicationStatus(system);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        // If the application status was retrieved, iterate through the results
        // and create a test object.
        if (applicationStatus != null) {
            for (Map.Entry<String, ServerStatus> entry 
                    : applicationStatus.entrySet()) {
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
    
    /**
     * Retrieves the system based on its unique name.
     * @param name The unique name of the system. 
     * @return The system, null if the system isn't found.
     */
    public System retrieveSystemByName(String name) {
        return this.systemDao.getSystemByName(name);
    }
    
    /**
     *  Creates and adds a test to a system based on its name.
     * @param systemName The name of the system.
     * @param result The result of the test.
     * @param type The type of test that has to be created and added.
     */
    public void addTest(String systemName, boolean result, TestType type){
        System system = this.systemDao.getSystemByName(systemName);
        List<Test> tests = system.getTests();
        java.util.Date date = new java.util.Date();
        Test test = new Test(type,new Timestamp(date.getTime()),result);
        tests.add(test);
        this.testDao.create(test);
        this.systemDao.edit(system);    
    }
    
    /**
     * Retrieves the last test for every type from a system.
     * @param system The system where the tests are requested from.
     * @return A list of the last test for every type of test.
     */
    public List<Test> retrieveLatestTests(System system) {
        List<Test> returnList = new ArrayList<>();
        returnList.add(testDao.retrieveLatestTestForTypeForSystem(system
                , TestType.STATUS));
        returnList.add(testDao.retrieveLatestTestForTypeForSystem(system
                , TestType.FUNCTIONAL));
        returnList.add(testDao.retrieveLatestTestForTypeForSystem(system
                , TestType.ENDPOINTS));
        return returnList;
    }
    
    /**
     * Tests the functional state of the systems. 
     */
    public void testFunctionalStateOfSystems() {
        checkRequestSender.requestChecks();
    }
}

