/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.MovementManager;
import business.RoadUsage;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alexander
 */
@Stateless
public class MovementService
{
    @Inject
    private MovementManager movementManager;
    
    @PostConstruct
    public void start() {
        System.out.println("Post construct MovementService");
    }
    
    // TODO
    /**
     *
     * @param cartrackerId
     * @param begin
     * @param end
     * @return
     */
    public List<RoadUsage> generateRoadUsages(Long cartrackerId, Date begin, 
            Date end)
    {
        System.out.println("generateRoadUsages");
        return movementManager.getRoadUsagesBetween(begin, end, cartrackerId);
    }
    
}
