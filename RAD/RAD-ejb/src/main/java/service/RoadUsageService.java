/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.RoadUsage;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.rest.clients.RoadUsagesClient;

/**
 * The service for road usages.
 *
 * @author Alexander
 */
@Stateless
public class RoadUsageService {

    @Inject
    private RoadUsagesClient roadUsagesClient;

    /**
     * Get the roadUsages for the given cartrackerId and the given month.
     *
     * @param cartrackerId The cartracker id to get the roadUsages for.
     * @param month The month to get the roadUsages for.
     * @param year The year to get the roadUsages for.
     * @return List of RoadUsage.
     */
    public List<RoadUsage> getRoadUsages(String cartrackerId, int month,
            int year) {
        return this.roadUsagesClient.getRoadUsages(cartrackerId, month,
                year);
    }
}
