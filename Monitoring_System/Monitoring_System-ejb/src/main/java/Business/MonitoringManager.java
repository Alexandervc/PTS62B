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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private @Inject SystemDao systemDao;
    private @Inject TestDao testDao;
    
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
    public final List<System> getSystems() {
        return this.systemDao.getSystems();
    }
    
    /**
     * Initializes the monitoring manager by creating a clientmap
     * and loading in the RMI servers.
     */
    @PostConstruct	
    public final void init() {
        this.clientMap = new HashMap<>();
        this.loadRMIServers();
    }

    
    
    
    /**
     * Generates the status of the server
     * @param system The system object where the status will be generated for.
     * @return A list
     */
    public final List<Test> generateServerStatus(System system) {
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
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        Test test = new Test(TestType.STATUS,sqlDate,result);
        
        tests.add(test);
        //Functional test
        try {
            monitoringClient.getFunctionalStatus();
            result = true;
        } catch(RemoteException ex) {
            result = false;
        }
        sqlDate = new java.sql.Date(new Date().getTime());
        test = new Test(TestType.FUNCTIONAL,sqlDate,result);
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
        sqlDate = new java.sql.Date(new Date().getTime());
        test = new Test(TestType.ENDPOINTS,sqlDate,result);
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
}
