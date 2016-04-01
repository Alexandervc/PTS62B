/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batch;

import business.MonitoringManager;
import javax.batch.api.Batchlet;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class InitBatch implements Batchlet {
    @Inject MonitoringManager manager;

    
    @Override
    public String process() throws Exception {
        for(Common.Domain.System sys : manager.getSystems()) {
            manager.generateServerStatus(sys);
        }
        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stops");
    }
    
}
