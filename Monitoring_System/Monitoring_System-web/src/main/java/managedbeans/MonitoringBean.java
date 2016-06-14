package managedbeans;

import com.google.gson.Gson;
import common.domain.System;
import common.domain.Test;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import service.MonitoringService;

/**
 *
 * @author Melanie.
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
        // Comment for sonarqube.
    }
    
    /**
     * Gets the systems that can be monitored.
     * @return The list of systems.
     */
    public List<System> getRetrieveSystems() {        
        this.retrieveSystems = this.service.retrieveSystems();
        return new ArrayList<>(this.retrieveSystems);
    }       
}
