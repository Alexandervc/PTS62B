/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.SystemDao;
import Data.TestDao;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class MonitoringManager {
    
    @Inject SystemDao systemDao;
    @Inject TestDao testDao;
    
    
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
     * @param server the Server object where the status will be generated for.
     * @return A list
     */
    public List<Object> generateServerStatus(Object server) {
        return null;
    }
}
