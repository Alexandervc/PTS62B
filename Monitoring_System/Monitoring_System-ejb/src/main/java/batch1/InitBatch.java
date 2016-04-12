/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batch1;

import business1.MonitoringManager;
import javax.batch.api.Batchlet;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class InitBatch implements Batchlet {
    private @Inject MonitoringManager manager;

    
    @Override
    public String process() throws Exception {

        this.manager.testFunctionalStateOfSystems();
        for(common1.domain1.System sys : this.manager.getSystems()) {
            this.manager.generateServerStatus(sys);
        }
        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stops");
    }
    
}
