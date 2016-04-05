/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import common.domain.System;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import service.MonitoringService;

/**
 *
 * @author Edwin
 */
@ManagedBean
@RequestScoped
public class MonitoringBean implements Serializable  {
    @EJB(beanName="monitoring")
    private MonitoringService service;
    
    private List<common.domain.System> retrieveSystems;

    /**
     * Empty constructor for sonarqube.
     */
    public MonitoringBean() {
        
    }
    
    
    
    /**
     * Gets the systems that can be monitored.
     * @return The list of systems.
     */
    public List<System> getRetrieveSystems() {
        
        this.retrieveSystems = this.service.retrieveSystems();
        System e = new System();
        e.setName("YALAYALA");
        this.retrieveSystems.add(e);
        return new ArrayList<>(this.retrieveSystems);
    }
       
}
