/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Timer;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;

/**
 *
 * @author Edwin.
 */
@Singleton
@Startup
public class BatchInitializer {
    
        
    // Interval between tests in minutes;
    private static final int TESTINTERVAL = 900000;    

    @Inject
    private MonitoringManager manager;

    @Resource
    private ManagedScheduledExecutorService executor;
    
    /**
     * Starts the job once the application is started.
     */
    @PostConstruct
    public void init() {
        TestRequestTask requestTask = new TestRequestTask();
        requestTask.setManager(this.manager);
        Timer timer = new Timer();

        // scheduling the task at interval
        timer.schedule(requestTask, 0, TESTINTERVAL);      
    }
   
}
