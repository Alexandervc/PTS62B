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
     * Generate the roadUsages between the given date for the given cartracker.
     * @param begin The begin date of the period to get the roadUsages between.
     *      Cannot be after end
     * @param end The end date of the period to get the roadUsages between.
     * @param cartrackerId The cartracker to get the roadUsages for.
     * @return The roadusages between the given dates for the given 
     *      cartrackerId.
    */
    public List<RoadUsage> generateRoadUsages(String cartrackerId, Date begin, 
            Date end) {
        return this.roadUsageManager.generateRoadUsagesBetween(begin, end, 
                cartrackerId);
    }
}
