/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.RoadUsage;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.rest.clients.RoadUsagesClient;

/**
 * The service for road usages.
 * @author Alexander
 */
@Stateless
public class RoadUsagesService {
    @Inject
    private RoadUsagesClient roadUsagesClient;
    
    /**
     * Get the roadUsages for the given cartrackerId between the given beginDate
     *      and endDate.
     * @param cartrackerId The cartracker id to get the roadUsages for.
     * @param beginDate The begin of the period to get the roadUsages 
     *      in between.
     * @param endDate The end of the period to get the roadUsages in between.
     * @return List of RoadUsage.
     */
    public List<RoadUsage> getRoadUsages(String cartrackerId, Date beginDate,
            Date endDate) {
        return this.roadUsagesClient.getRoadUsages(cartrackerId, beginDate, 
                endDate);
    }
}
