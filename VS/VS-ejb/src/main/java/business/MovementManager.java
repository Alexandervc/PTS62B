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
 *
 * @author Alexander
 */
@Stateless
public class MovementManager {
    @Inject
    private CarPositionDao carPositionDao;
    
    // TODO unittests
    public MovementManager() {
        this.carPositionDao = new CarPositionDao();
    }
    
    @PostConstruct
    public void start() {
        System.out.println("Post construct movementManager");
    }
    
    /**
     * 
     * @param begin cannot be after end
     * @param end
     * @param cartrackerId
     * @return The roadusages between the given date for the given cartrackerId
     */
    public List<RoadUsage> getRoadUsagesBetween(Date begin, Date end, 
            Long cartrackerId) {
        if(begin.after(end)) {
            throw new IllegalArgumentException("begin after end");
        }
        List<CarPosition> cps = carPositionDao.getPositionsBetween(begin, end, 
                cartrackerId);
        
        // Generate roadUsages
        Map<Road, RoadUsage> roadUsages = new HashMap<>();
        for(CarPosition cp : cps) {
            if(!roadUsages.containsKey(cp.getRoad())){
                // Add roadUsage
                RoadUsage ru = new RoadUsage(cp.getRoad().getName(), 
                                cp.getRoad().getRoadType(),
                                cp.getKm());
                roadUsages.put(cp.getRoad(), ru);
            } else {
                // Update km
                RoadUsage ru = (RoadUsage) roadUsages.get(cp.getRoad());
                ru.addKm(cp.getKm());
            }
        }
        
        // TODO verbeteren
        List<RoadUsage> roadUsagesList = new ArrayList<>();
        roadUsagesList.addAll(roadUsages.values());
        
        return roadUsagesList;//List;
    }
}
