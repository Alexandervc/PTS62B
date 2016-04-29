/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.inject.Inject;
import service.Scheduler;

/**
 *
 * @author Edwin
 */
@Singleton
@Startup
public class BatchInitializer {
    
        
    @Inject
    private MonitoringManager manager;
    
    //Interval between tests in minutes;
    private static int TESTINTERVAL = 900000;

    @Resource
    private ManagedScheduledExecutorService executor;
    
    /**
     * Starts the job once the application is started.
     */
    @PostConstruct
    public void init() {
        TestRequestTask requestTask = new TestRequestTask();
        requestTask.setManager(manager);
        Timer timer = new Timer();

        // scheduling the task at interval
        timer.schedule(requestTask, 0, 1000);      
    }
   
}
