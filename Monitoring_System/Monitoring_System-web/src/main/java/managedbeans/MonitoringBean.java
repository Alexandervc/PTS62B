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
    
    /**
     * Empty constructor for sonarqube.
     */
    public MonitoringBean() {
        
    }
    
    /**
     * Get entries for system.
     * 
     * @param sys.
     * @return List of entries.
     */
    public List<Entry<String, String>> getEntriesForSystem(common.domain.System sys) {
        List map = new ArrayList();
        List<List<Test>> tests = this.service.retrieveTests(sys);           

        for (List<Test> list_t : tests) { 
            List<Object> params = new ArrayList<>();
            //SystemState state = new SystemState(sys.getName());
            Boolean first = true;
            int[] date;

            for (Test t : list_t) {
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
                    //Set enddate for previous record on current date.
                    params.add(4, date);
                    Gson gson = new Gson();                    
                    String json = gson.toJson(params);                    
                    map.add(json);

                    params = new ArrayList<>();
                }

                params.add(0, sys.getName());

                String testtype = t.getTestType().toString();
                params.add(1, testtype);

                Boolean result = t.getResult();
                String testresult = "failed"; 

                if (result) {
                    testresult = "passed";
                }                

                params.add(2, testresult);

                params.add(3, date);

                first = false; 
            }

            //Sent enddate now for last record.
            Calendar calendar = Calendar.getInstance();
            date = new int[6];
            date[0] = calendar.get(Calendar.YEAR);
            date[1] = calendar.get(Calendar.MONTH);
            date[2] = calendar.get(Calendar.DAY_OF_WEEK);
            date[3] = calendar.get(Calendar.HOUR_OF_DAY);
            date[4] = calendar.get(Calendar.MINUTE);
            date[5] = calendar.get(Calendar.SECOND);

            params.add(4, date);
            Gson gson = new Gson();
            String json = gson.toJson(params);
            map.add(json);
        }           
        
        return map;
    }

    public void setEntries(List<Entry<String, String>> entries) {
        this.entries = entries;
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
