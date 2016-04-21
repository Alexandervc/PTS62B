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
 * @author Edwin.
 */
public class InitBatch implements Batchlet {
    @Inject
    private MonitoringManager manager;     
    
    /**
     * Starts the processing of the batch.
     * @return The result of the process.
     * @throws Exception if anything goes wrong an Exception will be thrown.
     */
    @Override
    public String process() throws Exception {

        this.manager.testFunctionalStateOfSystems();
        for(common.domain.System sys : this.manager.getSystems()) {
            this.manager.generateServerStatus(sys);
        }
        return "COMPLETED";
    }

    /**
     * Method called when the batch job stops. Currently has no usage.
     * @throws Exception .
     */
    @Override
    public void stop() throws Exception {
        //Stops the method, currently no usage.
    }
    
}
