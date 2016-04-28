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
import common.domain.ServerStatus;
import common.domain.System;
import common.domain.Test;
import common.domain.TestType;
import data.SystemDao;
import data.TestDao;
import data.jms.CheckRequestSender;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

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
     *
     * @return A list of servers.
     */
    public List<System> getSystems() {
        return this.systemDao.getSystems();
    }

    /**
     * Retrieves the server status and maps it to a Test object.
     *
     * @param system The system to retrieve the status from.
     * @return A test object with the result of the test
     */
    private Test retrieveServerStatus(System system) {
        List<Test> tests = new ArrayList<>();
        Map<String, ServerStatus> applicationStatus = null;

        // Try to get the status of all deployed applications of the system.
        try {
            applicationStatus
                    = this.serverStatusManager.retrieveApplicationStatus(system);
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
     * Retrieves the system based on its unique name.
     *
     * @param name The unique name of the system.
     * @return The system, null if the system isn't found.
     */
    public System retrieveSystemByName(String name) {
        return this.systemDao.getSystemByName(name);
    }

    /**
     * Creates and adds a test to a system based on its name.
     *
     * @param systemName The name of the system.
     * @param result The result of the test.
     * @param type The type of test that has to be created and added.
     */
    public void addTest(String systemName, boolean result, TestType type) {
        System system = this.systemDao.getSystemByName(systemName);
        List<Test> tests = system.getTests();
        java.util.Date date = new java.util.Date();
        Test test = new Test(type, new Timestamp(date.getTime()), result);
        tests.add(test);
        this.testDao.create(test);
        this.systemDao.edit(system);
    }

    /**
     * Retrieves the last test for every type from a system.
     *
     * @param system The system where the tests are requested from.
     * @return A list of the last test for every type of test.
     */
    public List<Test> retrieveLatestTests(System system) {
        List<Test> returnList = new ArrayList<>();
        returnList.add(this.testDao.retrieveLatestTestForTypeForSystem(system, TestType.STATUS));
        returnList.add(this.testDao.retrieveLatestTestForTypeForSystem(system, TestType.FUNCTIONAL));
        returnList.add(this.testDao.retrieveLatestTestForTypeForSystem(system, TestType.ENDPOINTS));
        return returnList;
    }

    /**
     * Tests the functional state of the systems.
     */
    public void testFunctionalStateOfSystems() {
        this.checkRequestSender.requestChecks();
    }

    /**
     * Tests all systems on 3 test types. Functional, Endpoints and Status.
     * Stores the result.
     */
    public void testSystems() {
        // Sends a message into the topic so the systems can send their test
        // results.
        this.checkRequestSender.requestChecks();

        // For every system retrives the server status, adds this as a test
        // to the database. Creates failed tests for functional and endpoints.
        for (System s : this.getSystems()) {
            Test testStatus = this.retrieveServerStatus(s);
            s.addTest(testStatus);
            this.testDao.create(testStatus);

            java.util.Date date = new java.util.Date();
            Timestamp currentDate = new Timestamp(date.getTime());

            // Creates tests that will be fixed later.
            Test testEndpoints
                    = new Test(TestType.ENDPOINTS, currentDate, false);
            
            Boolean testResult = false;

            // Get testResults from jenkins
            try {
                JSONObject json = readJsonFromUrl(
                        "http://192.168.24.70:8070/job/PTS-S62B-"
                        + s.getName()
                        + "/lastSuccessfulBuild/testReport/api/json");
                String result = (String) json.get("failCount");
                // If result "failCount" == 0 testResult is true
                if (result.equals("0")) {
                    testResult = Boolean.TRUE;
                }
            } catch (IOException | JSONException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            // create testFunctional with correct result
            Test testFunctional
                    = new Test(TestType.FUNCTIONAL, currentDate, testResult);
            s.addTest(testFunctional);
            this.testDao.create(testFunctional);

            // Stores all the tests in the database.
            this.testDao.create(testEndpoints);

            this.systemDao.edit(s);
        }
    }

    /**
     * Read JSON from jenkins.
     *
     * @param url correct url for testresults.
     * @return JSON object with testresults.
     * @throws IOException fail at reading inputstream.
     * @throws JSONException fail at JSON parse.
     */
    public static JSONObject readJsonFromUrl(String url)
            throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                    Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();

            JSONObject json = new JSONObject(jsonText);

            return json;
        } finally {
            is.close();
        }
    }

}
