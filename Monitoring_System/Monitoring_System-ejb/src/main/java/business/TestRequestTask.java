/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.TimerTask;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class TestRequestTask extends TimerTask {

    private MonitoringManager manager;     

    public MonitoringManager getManager() {
        return manager;
    }

    public void setManager(MonitoringManager manager) {
        this.manager = manager;
    }
    
    
    
    @Override
    public void run() {
        System.out.println("TESTING SYSTEMS");
        manager.testSystems();
    }
    
}
