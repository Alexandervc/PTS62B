/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import common.domain.ConnectionClient;
import common.domain.MethodTest;
import common.domain.System;
import common.domain.Test;
import common.domain.TestType;
import data.IMonitoring;
import data.RMI_Client;
import data.SystemDao;
import data.TestDao;
import data.VSinterface;
import data.testinject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    SystemDao systemDao;
    @Inject TestDao testDao;
    @Inject testinject inject;
    
    private final static Logger LOGGER = Logger.getLogger(MonitoringManager.class.getName()); 

    
    /**
     * Empty constructor for sonarqube.
     */
    public MonitoringManager() {
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
        this.loadRMIServers();
    }

    /**
     * Generates the status of the server
     * @param system The system object where the status will be generated for.
     * @return A list
     */
    public  List<Test> generateServerStatus(System system) {
        RMI_Client client = clientMap.get(system.getName());
        IMonitoring monitoringClient = client.getMonitoringClient(system.getName());
        List<Test> tests = new ArrayList<>();
        //Status test
        boolean result;
        try {
            monitoringClient.getServerStatus();
            result = true;
        } catch(RemoteException ex) {
            result = false;
        }
        Test test = new Test(TestType.STATUS,new Timestamp(java.lang.System.currentTimeMillis()),result);
        
        tests.add(test);
        //Functional test
        try {
            monitoringClient.getFunctionalStatus();
            result = true;
        } catch(RemoteException ex) {
            result = false;
        }
        test = new Test(TestType.FUNCTIONAL,new Timestamp(java.lang.System.currentTimeMillis()),result);
        tests.add(test);

        result = true;
        //Endpoints test
        for(ConnectionClient cClient : system.getClients()) {
            String bindingName = cClient.getName();
            VSinterface Vs = client.getVSClient(bindingName);
            List<common.domain.Method> methods = cClient.getMethods();
            //gets all the methods in the client interface.
            for(Method m :Vs.getClass().getMethods()) {
                boolean testResult = false;
                //tests if the endpoint is reachable.
                try {
                    m.invoke(Vs);
                    testResult = true;
                } catch(InvocationTargetException e) {
                    if(e.getTargetException().getClass().getClass().equals(RemoteException.class)) {
                        testResult = false;
                        result = false;
                    }
                } catch(IllegalAccessException | IllegalArgumentException e) {
                    testResult = false;
                    result = false;
                }
                //checks if the method is already known.
                Boolean known = false;
                common.domain.Method dbMethod = null;
              
                for(common.domain.Method i : methods) {
                    if(i.getName().equals(m.getName())) {
                        known = true;
                        dbMethod = i;
                    }
                }
                
                //if the method isn't known adds it to the database.
                if(!known) {
                    dbMethod = new common.domain.Method(m.getName());
                    methods.add(dbMethod);
                }
                
                List<MethodTest> methodTests = dbMethod.getTests();
                java.util.Date date= new java.util.Date();
                MethodTest mt = new MethodTest(new Timestamp(date.getTime()),testResult);
                methodTests.add(mt);   
                dbMethod.setTests(methodTests);
            }       
        }
        test = new Test(TestType.ENDPOINTS,new Timestamp(java.lang.System.currentTimeMillis()),result);
        tests.add(test);
        return tests;
    }
    
    private void loadRMIServers() {
        for(System sys : this.getSystems()) {
            RMI_Client client = new RMI_Client(sys.getIp(),
                                    sys.getPort());
            this.clientMap.put(sys.getName(), client);
        }
    }

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
    
    public System retrieveSystemByName(String name) {
        return this.systemDao.getSystemByName(name);
    }
    
    public void addTest(String systemName, boolean result, TestType type){
        System system = this.systemDao.getSystemByName(systemName);
        List<Test> tests = system.getTests();
        java.util.Date date = new java.util.Date();
        Test test = new Test(type,new Timestamp(date.getTime()),result);
        tests.add(test);
        this.testDao.create(test);
        this.systemDao.edit(system);    
    }
}
