/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import common.domain.System;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *  Dao used to store System data in the database using JPA.
 * @author Edwin.
 */
@Stateless
public class SystemDao extends AbstractDao {

    private static final Logger LOGGER = 
            Logger.getLogger(SystemDao.class.getName()); 

    @Inject 
    @MonitoringDB2 
    private EntityManager em;
    
    /**
     * Creates a systemDao using the extended AbstractDao.
     */
    public SystemDao() {
        super(System.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Retrieves a list of all the systems.
     * @return A list of systems.
     */
    public List<System> getSystems() {
        Query query = this.getEntityManager().createNamedQuery("get systems");
        return query.getResultList();
    }
    
    /**
     * Gets a system with the name that is given.
     * @param name The name that the system has to have.
     * @return The system with the name.
     */
    public System getSystemByName(String name) {
        Query query = this.getEntityManager()
                .createNamedQuery("get system by name");
        query.setParameter("name", name);   
        return (System) query.getSingleResult();
    }
    
}
