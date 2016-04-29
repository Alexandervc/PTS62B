/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.MonitoringManager;
import common.domain.Test;
import common.domain.TestType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.inject.Inject;

/**
 * The service that contains methods concerning the monitoring of servers.
 *
 * @author Edwin
 */
@Stateless(name = "monitoring")
public class MonitoringService {

    @Inject
    private MonitoringManager manager;

    /**
     * Empty constructor for JPA usage.
     */
    public MonitoringService() {
        // Comment for sonarqube.
    }

    

    /**
     * Retrieves a list of Systems that are currently part of the RRA
     * application.
     *
     * @return A list of systems.
     */
    public List<common.domain.System> retrieveSystems() {
        return this.manager.getSystems();
    }

    /**
     * Retrieves the last versions of the tests. One for each test type.
     *
     * @param system The system that the tests have to be retrieved for.
     * @return A list with the 3 tests.
     */
    public List<Test> retrieveLatestTests(common.domain.System system) {

        // TODO: Get historical tests.
        return this.manager.retrieveLatestTests(system);
    }

    /**
     * Processes the test results of a functional and endpoints test
     * and stores them.
     *
     * @param systemName Name of system
     */
    public void processTestResults(String systemName) {
        this.manager.addTest(systemName, true, TestType.ENDPOINTS);
    }  
}
