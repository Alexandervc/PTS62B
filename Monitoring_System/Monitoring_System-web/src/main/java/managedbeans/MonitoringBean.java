/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import common.domain.System;
import common.domain.Test;
import common.domain.TestType;
import domain.SystemState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    private List<String> systemStrings;
    
    private List<Entry<String, String>> entries;

    public List<Entry<String, String>> getEntries() {
        Map map = new HashMap();
        for(common.domain.System sys : this.getRetrieveSystems()) {
            SystemState state = new SystemState(sys.getName());
            for(Test t : service.retrieveLatestTests(sys)) {
                switch (t.getTestType()) {
                    case FUNCTIONAL:
                        state.setFunctional(t.getResult().toString());
                        break;
                    case ENDPOINTS:
                        state.setEndpoints(t.getResult().toString());
                        break;
                    case STATUS:
                        state.setStatus(t.getResult().toString());
                        break;
                    default:
                        break;
                }
                com.google.gson.Gson gson = new com.google.gson.Gson();
                map.put(sys.getName(),gson.toJson(state));
            }
        }
        entries = new ArrayList<>(map.entrySet());
        return entries;
    }

    public void setEntries(List<Entry<String, String>> entries) {
        this.entries = entries;
    }
    
    private Map<String, SystemState> map;

    public Map<String, SystemState> getMap() {
        this.map = new HashMap();
        for(common.domain.System sys : retrieveSystems) {
            this.map.put(sys.getName(), new SystemState(sys.getName()));
        }
        return map;
    }

    public void setMap(Map<String, SystemState> map) {
        this.map = map;
    } 
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
       /* System e = new System();
        e.setName("YALAYALA");
        this.retrieveSystems.add(e);*/
        return new ArrayList<>(this.retrieveSystems);
    }
       
}
