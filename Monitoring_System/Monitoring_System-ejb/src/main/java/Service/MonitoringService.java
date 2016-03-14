/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Edwin
 */
@Stateless
@LocalBean
public class MonitoringService {
    //Test


    /**
     * Retrieves a list of servers that are currently part of the RRA
     * application.
     * @return A list of servers.
     */
    public List<Object> retrieveServers() {
        return null;       
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
