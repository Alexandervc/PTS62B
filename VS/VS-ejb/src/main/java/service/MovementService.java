/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.MovementManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class MovementService extends UnicastRemoteObject implements IMovementService {
    @Inject
    private MovementManager movementManager;
    
    @Deprecated
    public MovementService() throws RemoteException {
        this.movementManager = new MovementManager();
    }
    
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
     * @throws RemoteException
     */
    @Override
    public List<IRoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) throws RemoteException {
        System.out.println("generateRoadUsages");
        return movementManager.getRoadUsagesBetween(begin, end, cartrackerId);
    }
    
}
