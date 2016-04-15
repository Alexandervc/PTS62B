/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import domain.CarPosition;
import domain.Road;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The manager of roadUsages.
 * @author Alexander
 */
@Stateless
public class RoadUsageManager {
    @Inject
    private CarPositionDao carPositionDao;
    
    /**
     * Generate the roadUsages between the given date for the given cartracker.
     * @param begin The begin date of the period to get the roadUsages between.
     *      Cannot be after end
     * @param end The end date of the period to get the roadUsages between.
     * @param cartrackerId The cartracker to get the roadUsages for.
     * @return The roadusages between the given dates for the given 
     *      cartrackerId.
     */
    public List<RoadUsage> generateRoadUsagesBetween(Date begin, Date end, 
            Long cartrackerId) {
        if(begin.after(end)) {
            throw new IllegalArgumentException("begin after end");
        }
        
        // Get carPositions
        List<CarPosition> cps = this.carPositionDao.getPositionsBetween(begin, 
                end, cartrackerId);
        
        // Make roadUsages from carPositions
        Map<Road, RoadUsage> roadUsages = new HashMap<>();
        for(CarPosition cp : cps) {
            if(!roadUsages.containsKey(cp.getRoad())){
                // Add roadUsage
                RoadUsage ru = new RoadUsage(cp.getRoad().getName(), 
                                cp.getRoad().getRoadType(),
                                cp.getMeter());
                roadUsages.put(cp.getRoad(), ru);
            } else {
                // Update km
                RoadUsage ru = (RoadUsage) roadUsages.get(cp.getRoad());
                ru.addMeter(cp.getMeter());
            }
        }
        
        List<RoadUsage> roadUsagesList = new ArrayList<>(roadUsages.values());
        return roadUsagesList;
    }
}
