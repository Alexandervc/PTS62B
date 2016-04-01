/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Common.Domain.System;
import Service.MonitoringService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Edwin
 */
@ManagedBean
@RequestScoped
public class MonitoringBean implements Serializable  {
    @EJB(beanName="monitoring")
    private MonitoringService service;
    
    private List<Common.Domain.System> retrieveSystems;
    

    public List<System> getRetrieveSystems() {
        return service.retrieveSystems();
    }

    public void setRetrieveSystems(List<System> retrieveSystems) {
        this.retrieveSystems = retrieveSystems;
    }
       
}
