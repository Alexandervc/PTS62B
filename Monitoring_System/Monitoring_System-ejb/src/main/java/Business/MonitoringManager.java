/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import Common.Domain.ConnectionClient;
import Common.Domain.MethodTest;
import Common.Domain.System;
import Common.Domain.Test;
import Common.Domain.TestType;
import Data.IMonitoring;
import Data.RMI_Client;
import Data.SystemDao;
import Data.TestDao;
import Data.VSinterface;
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

    public MonitoringManager() {
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
            List<Common.Domain.Method> methods = cClient.getMethods();
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
                Common.Domain.Method dbMethod = null;
              
                for(Common.Domain.Method i : methods) {
                    if(i.getName().equals(m.getName())) {
                        known = true;
                        dbMethod = i;
                    }
                }
                
                //if the method isn't known adds it to the database.
                if(!known) {
                    dbMethod = new Common.Domain.Method(m.getName());
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
            RMI_Client client = new RMI_Client(sys.getIP(),
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
