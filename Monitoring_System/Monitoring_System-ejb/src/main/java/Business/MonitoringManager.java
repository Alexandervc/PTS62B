/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.IMonitoring;
import Data.RMI_Client;
import Data.SystemDao;
import Data.TestDao;
import Common.Domain.System;
import Common.Domain.Test;
import Common.Domain.TestType;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class MonitoringManager {

    public MonitoringManager() {
        this.clientMap = new HashMap<>();
        this.loadRMIServers();
    }
    
    @Inject SystemDao systemDao;
    @Inject TestDao testDao;
    private Map<String,IMonitoring> clientMap;
    
    /**
     * Retrieves a list of Systems that are currently part of the RRA
     * application.
     * @return A list of servers.
     */
    public List<System> getSystems() {
        return systemDao.getSystems();
    }
    
    /**
     * Generates the status of the server
     * @param system The system object where the status will be generated for.
     * @return A list
     */
    public List<Test> generateServerStatus(System system) {
        IMonitoring client = clientMap.get(system.getName());
        List<Test> tests = new ArrayList<>();
        //Status test
        boolean result;
        try {
            client.getServerStatus();
            result = true;
        } catch(RemoteException ex) {
            result = false;
        }
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        Test test = new Test(TestType.STATUS,sqlDate,result);
        
        tests.add(test);
        //Functional test
        try {
            client.getFunctionalStatus();
            result = true;
        } catch(RemoteException ex) {
            result = false;
        }
        sqlDate = new java.sql.Date(new Date().getTime());
        test = new Test(TestType.FUNCTIONEEL,sqlDate,result);
        tests.add(test);

        return tests;
    }
    
    private void loadRMIServers() {
        for(System sys : this.getSystems()) {
            RMI_Client client = new RMI_Client(sys.getIP(),
                                    sys.getPort(),
                                    sys.getName());
            clientMap.put(sys.getName(), client.getMonitoringClient());
        }
    }
}
