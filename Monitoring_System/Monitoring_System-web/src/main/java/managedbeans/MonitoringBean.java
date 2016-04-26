package managedbeans;

import com.google.gson.Gson;
import common.domain.System;
import common.domain.Test;
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
import javax.persistence.Transient;
import service.MonitoringService;

/**
 *
 * @author Edwin.
 */
@ManagedBean
@RequestScoped
public class MonitoringBean implements Serializable  {
    @EJB(beanName="monitoring")
    private MonitoringService service;
    
    private List<common.domain.System> retrieveSystems;

    private List<Entry<String, String>> entries;

    public List<Entry<String, String>> getEntries() {
        Map hMap = new HashMap();
        for(common.domain.System sys : this.getRetrieveSystems()) {
            SystemState state = new SystemState(sys.getName());
            for(Test t : this.service.retrieveLatestTests(sys)) {
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
                
                Gson gson = new Gson();
                hMap.put(sys.getName(), gson.toJson(state));
            }
        }
        
        this.entries = new ArrayList<>(hMap.entrySet());
        return this.entries;
    }

    public void setEntries(List<Entry<String, String>> entries) {
        this.entries = new ArrayList(entries    );
    }
   
    /*
    private Map<String, List<Object>> map;

    public Map<String, List<Object>> getMap() {
        this.map = new HashMap();
        
        for(common.domain.System sys : retrieveSystems) {
            List<Test> tests = service.retrieveLatestTests(sys);            
            List<Object> params = new ArrayList<>();
            Boolean first = true;
            int[] date;
            
            for (Test t : tests) {   
                Timestamp timestamp = t.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp.getTime());

                date = new int[6];
                date[0] = calendar.get(Calendar.YEAR);
                date[1] = calendar.get(Calendar.MONTH);
                date[2] = calendar.get(Calendar.DAY_OF_WEEK);
                date[3] = calendar.get(Calendar.HOUR_OF_DAY);
                date[4] = calendar.get(Calendar.MINUTE);
                date[5] = calendar.get(Calendar.SECOND);
                
                if (!first) {
                    //Set enddate for previous record on current date
                    params.add(2, date);
                    this.map.put(sys.getName(), params);
                    params = new ArrayList<>();
                }
                
                SystemState state = new SystemState(sys.getName());
                params.add(0, state);
                
                params.add(1, date);
                
                first = false;    
            }
            
            //Sent enddate now for last record
            Calendar calendar = Calendar.getInstance();
            date = new int[6];
            date[0] = calendar.get(Calendar.YEAR);
            date[1] = calendar.get(Calendar.MONTH);
            date[2] = calendar.get(Calendar.DAY_OF_WEEK);
            date[3] = calendar.get(Calendar.HOUR_OF_DAY);
            date[4] = calendar.get(Calendar.MINUTE);
            date[5] = calendar.get(Calendar.SECOND);
            
            params.add(2, date);
            this.map.put(sys.getName(), params);
        }
        
        return map;
    }
    
    public void setMap(Map<String, List<Object>> map) {
        this.map = map;
    }  
    */
    
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
