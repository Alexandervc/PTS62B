/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import common.domain.Test;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Parser that turns tests into a structure that can be turned into
 * JSON.
 * @author Edwin
 */
public class TestsParser {

    private TestsParser() {
        // Private constructor to hide public one.
    }
    
    
    
    /**
     * Due to nature of the method should practically only be used after 
     * MonitoringService.retrieveTests
     * @param tests The list of tests that has to be parsed.
     * @param sys The system that the tests are from
     * @return A JSON string of a map, of entries, with 2 strings.
     */
    public static List<Map.Entry<String, String>> parseSystemTests(
            List<List<Test>> tests,
            common.domain.System sys) {
        List map = new ArrayList();

        Gson gson = new Gson();                    

        for (List<Test> list_t : tests) { 
            List<Object> params = new ArrayList<>();
            Boolean first = true;
            Boolean prevResult = null;
            int[] date;

            for (Test t : list_t) {
                Timestamp timestamp = t.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp.getTime());

                date = new int[6];
                date[0] = calendar.get(Calendar.YEAR);
                date[1] = calendar.get(Calendar.MONTH);
                date[2] = calendar.get(Calendar.DAY_OF_MONTH);
                date[3] = calendar.get(Calendar.HOUR_OF_DAY);
                date[4] = calendar.get(Calendar.MINUTE);
                date[5] = calendar.get(Calendar.SECOND);
                
                if (prevResult != null && prevResult.booleanValue() == 
                        t.getResult().booleanValue()) {
                    //If testresult is the same as the previous testresult,
                    //replace the enddate.
                    if (params.size() >= 5) {
                        params.set(4, date);
                    } else {
                        params.add(4, date);
                    }
                } else {
                    //If current testresult is the first record, enddate of 
                    //previous test can't be set.
                    if (!first) {
                        //Set enddate for previous record on current date.
                        if (params.size() >= 5) {
                            params.set(4, date);
                        } else {
                            params.add(4, date);
                        }
                        
                        String json = gson.toJson(params);                    
                        map.add(json);

                        //Start new parameters list for current testresult
                        params = new ArrayList<>();
                    }
                    
                    params.add(0, sys.getName());

                    String testtype = t.getTestType().toString();
                    params.add(1, testtype);

                    Boolean result = t.getResult();
                    prevResult = result;
                    String testresult = "failed"; 

                    if (result) {
                        testresult = "passed";
                    }                

                    params.add(2, testresult);

                    params.add(3, date);
                }

                first = false; 
            }

            //Set enddate now for last record.
            Calendar calendar = Calendar.getInstance();
            date = new int[6];
            date[0] = calendar.get(Calendar.YEAR);
            date[1] = calendar.get(Calendar.MONTH);
            date[2] = calendar.get(Calendar.DAY_OF_MONTH);
            date[3] = calendar.get(Calendar.HOUR_OF_DAY);
            date[4] = calendar.get(Calendar.MINUTE);
            date[5] = calendar.get(Calendar.SECOND);

            if (params.size() >= 5) {
                params.set(4, date);
            } else {
                params.add(4, date);
            }
            
            String json = gson.toJson(params);
            map.add(json);
        }           
        
        return map;
    }
}
