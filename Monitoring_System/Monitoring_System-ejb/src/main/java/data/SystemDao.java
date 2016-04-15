/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.MonitoringManager;
import common.domain.Test;
import common.domain.System;
import java.util.logging.Logger;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Edwin
 */
@Stateless
public class SystemDao extends AbstractDao {

    private final static Logger LOGGER = Logger.getLogger(SystemDao.class.getName()); 

    @Inject @MonitoringDB2 
    private EntityManager em;


    @PostConstruct
    public void init() {
        LOGGER.log(Level.INFO, "SystemDao created");

    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SystemDao() {
        super(System.class);
    }

    public List<System> getSystems() {
        Query query = this.getEntityManager().createNamedQuery("get systems");
        return query.getResultList();
    }
    
    public System getSystemByName(String name) {
        Query query = this.getEntityManager().createNamedQuery("get system by name");
        query.setParameter("name", name);   
        return (System) query.getSingleResult();
    }
    
}