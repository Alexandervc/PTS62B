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
    
    //Interval between tests in minutes.
    private static int TESTINTERVAL = 15;

    @Resource
    private ManagedScheduledExecutorService executor;

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
     * Generates the status of the server
     *
     * @param system the Server object where the status will be generated for.
     * @return A list
     */
    public void generateServerStatus(common.domain.System system) {
        this.manager.generateServerStatus(system);
    }
    
    /**
     * Retrives all tests. One for each test type.
     *
     * @param system The system that the tests have to be retrieved for.
     * @return A list with the 3 tests.
     */
    public List<List<Test>> retrieveTests(common.domain.System system) {

        // TODO: Get historical tests.
        return this.manager.retrieveTests(system);
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
