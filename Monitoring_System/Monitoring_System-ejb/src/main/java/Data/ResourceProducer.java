/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Edwin
 */

@Stateless
public class ResourceProducer {
    //@PersistenceContext(unitName = "Proftaak")
    private EntityManager em;
    
    @Produces
    @MonitoringDB
    public EntityManager createMonitoringDB() {
        em = Persistence.createEntityManagerFactory("Proftaak").createEntityManager();
        return em;
    }
    
}
