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
     * @return A list
     */
    public final List<Test> generateServerStatus(System system) {
        return null;
    }
}
