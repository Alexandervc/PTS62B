/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Produces resources so they can be accessed across the application.
 * @author Edwin.
 */
@Stateless
public class ResourceProducer {
    @PersistenceContext(unitName = "Proftaak")
    private EntityManager em;
    
    /**
     * Creates a database for monitoring.
     * @return The entity manager for the monitoring DB.
     */
    @Produces
    @MonitoringDB2  
    public EntityManager createMonitoringDB() {
        return this.em;
    }
    
}
