/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import dao.CarPositionDao;
import domain.CarPosition;
import domain.Road;
import dto.RoadUsage;

/**
 * The manager of roadUsages.
 * @author Alexander
 */
@Stateless
public class RoadUsageManager {
    @Inject
    private CarPositionDao carPositionDao;
    
    /**
     * Convert the given carpositions to a list of roadUsages.
     * @param carpositions The carpositions to convert.
     * @return List of roadUsage.
     */
    public List<RoadUsage> convertToRoadUsages(List<CarPosition> carpositions) {
        // Make roadUsages from carPositions
        Map<Road, RoadUsage> roadUsages = new HashMap<>();
        for(CarPosition cp : carpositions) {
            if(!roadUsages.containsKey(cp.getRoad())) {
                RoadUsage ru = null;
                
                if (cp.getRideId().startsWith("PT")) {
                    // Add roadUsage
                    ru = new RoadUsage(cp.getRoad().getName(), 
                                    cp.getRoad().getRoadType(),
                                    cp.getMeter(),
                                    Long.parseLong(
                                            cp.getRideId().substring(2)));
                } else {
                    // Add roadUsage
                    ru = new RoadUsage(cp.getRoad().getName(), 
                                    cp.getRoad().getRoadType(),
                                    cp.getMeter());
                }
                
                roadUsages.put(cp.getRoad(), ru);
            } else {
                // Update km
                RoadUsage ru = (RoadUsage) roadUsages.get(cp.getRoad());
                ru.addMeter(cp.getMeter());
            }
        }
        
        return new ArrayList<>(roadUsages.values());
    }
    
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
            String cartrackerId) {
        if(begin.after(end)) {
            throw new IllegalArgumentException("begin after end");
        }
        
        // Get carPositions
        List<CarPosition> cps = this.carPositionDao.getPositionsBetween(begin, 
                end, cartrackerId);
        
        return this.convertToRoadUsages(cps);
    }
}
