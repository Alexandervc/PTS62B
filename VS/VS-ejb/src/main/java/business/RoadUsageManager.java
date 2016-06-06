/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;
import java.util.List;
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
        List<RoadUsage> roadUsages = new ArrayList<>();
        
        for (CarPosition cp : carpositions) {
            if (cp.getRideId() == null) { // Foreign CarPosition.
                // If the CarPosition is foreign and its ForeignCountryRideId
                // has not yet been added to roadUsages list, create a RoadUsage
                // and add it to the roadUsages list.
                if (!this.roadUsagesContainsForeign(
                        roadUsages,
                        cp.getForeignCountryRideId())) {
                    
                    roadUsages.add(
                            new RoadUsage(cp.getRoad().getName(), 
                                          cp.getRoad().getRoadType(),
                                          cp.getMeter(),
                                          cp.getForeignCountryRideId()));
                }
            } else { // Internal CarPosition.
                if (!this.roadUsagesContainsRoad(roadUsages, cp.getRoad())) {
                    // If the CarPosition is internal and its Road has not yet
                    // been added to the roadUsages list, create a RoadUsage
                    // and add it to the roadUsages list.
                    roadUsages.add(
                            new RoadUsage(cp.getRoad().getName(), 
                                          cp.getRoad().getRoadType(),
                                          cp.getMeter()));
                } else {
                    // If the internal CarPosition's Road has already been added
                    // to the roadUsages list, update the distance of this
                    // RoadUsage.
                    for (RoadUsage roadUsage : roadUsages) {
                        if (cp.getRoad().getName()
                                .equals(roadUsage.getRoadName())) {
                            roadUsage.addMeter(cp.getMeter());
                            break;
                        }
                    }
                }
                       
            }
        }
        
        return roadUsages;
    }
    
    /**
     * Checks if the RoadUsages list contains the Road by RoadName.
     * @param roadUsages The list in which to check if it contains the Road.
     * @param road The road to check for.
     * @return true if the list contains the road, otherwise false.
     */
    private boolean roadUsagesContainsRoad(List<RoadUsage> roadUsages,
                                           Road road) {
        for (RoadUsage roadUsage : roadUsages) {
            if (road.getName().equals(roadUsage.getRoadName())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if the RoadUsages list contains the ForeignCountryRideId.
     * @param roadUsages The list in which to check if it contains the
     *      ForeignCountryRideId.
     * @param foreignCountryRideId The foreignCountryRideId to check for.
     * @return true if the list contains the ForeignCountryRideId, otherwise
     *      false.
     */
    private boolean roadUsagesContainsForeign(List<RoadUsage> roadUsages,
                                              Long foreignCountryRideId) {        
        for (RoadUsage roadUsage : roadUsages) {
            if (foreignCountryRideId.equals(
                    roadUsage.getForeignCountryRideId())) {
                return true;
            }
        }
        
        return false;
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
