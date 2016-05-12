/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import business.RoadUsageManager;
import dto.RoadUsage;

/**
 * The service for roadUsage.
 * @author Alexander
 */
@Stateless
public class RoadUsageService {
    @Inject
    private RoadUsageManager roadUsageManager;

    /**
     * Generate the roadUsages for the given month for the given cartracker.
     * @param month The month to get the roadUsages for.
     * @param year The year to get the roadUsages for.
     * @param cartrackerId The cartracker to get the roadUsages for.
     * @return The roadusages between the given dates for the given 
     *      cartrackerId.
    */
    public List<RoadUsage> generateRoadUsages(String cartrackerId, int month, 
            int year) {
        return this.roadUsageManager.generateRoadUsagesOfMonth(month, year, 
                cartrackerId);
    }
}
