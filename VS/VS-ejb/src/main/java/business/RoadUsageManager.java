/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
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
                RoadUsage ru;
                
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
     * Generate the roadUsages for the given month for the given cartracker.
     * @param month The month to generate the roadUsages for.
     * @param year The year to generate the roadUsages for.
     * @param cartrackerId The cartracker to get the roadUsages for.
     * @return The roadusages between the given dates for the given 
     *      cartrackerId.
     */
    public List<RoadUsage> generateRoadUsagesOfMonth(int month, int year, 
            String cartrackerId) {        
        // Get carPositions
        List<CarPosition> cps = this.carPositionDao.getPositionsOfMonth(month, 
                year, cartrackerId);
        
        List<RoadUsage> roadUsages = this.convertToRoadUsages(cps);
        roadUsages.sort(null);
        return roadUsages;
    }
}
