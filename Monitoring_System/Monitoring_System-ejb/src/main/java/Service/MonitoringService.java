/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Business.MonitoringManager;
import Common.Domain.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.inject.Inject;

/**
 * The service that contains methods concerning the monitoring of servers
 * @author Edwin
 */
@Stateless
@LocalBean
public class MonitoringService {
    //Test

    @PostConstruct
    public void init() {
        runJob();
    }
    
    @Inject MonitoringManager manager;

    @Resource
    ManagedScheduledExecutorService executor;
 
    public void runJob() {
        executor.schedule(new Scheduler(), new Trigger() {
 
            @Override
            public Date getNextRunTime(LastExecution lastExecutionInfo, Date taskScheduledTime) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(taskScheduledTime);
                cal.add(Calendar.MINUTE, 15);
                return (Date) cal.getTime();
            }
 
            @Override
            public boolean skipRun(LastExecution lastExecutionInfo, Date scheduledRunTime) {
                return null == lastExecutionInfo;
            }
        });
    }
 
    public void cancelJob() {
        executor.shutdown();
    }

    /**
     * Retrieves a list of Systems that are currently part of the RRA
     * application.
     * @return A list of systems.
     */
    public List<Common.Domain.System> retrieveSystems() {
        return manager.getSystems();
    }
    
    /**
     * Generates the status of the server
     * @param system the Server object where the status will be generated for.
     * @return A list
     */
    public List<Test> generateServerStatus(Common.Domain.System system) {
        return this.manager.generateServerStatus(system);
    }
    
    public List<Test> retrieveLatestTests(Common.Domain.System system) {
        return this.manager.retrieveLatestTests(system);
    }
}
