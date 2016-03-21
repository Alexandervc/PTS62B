/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Alexander
 */
@Stateless
public class MovementService extends UnicastRemoteObject implements IMovementService {

    public MovementService() throws RemoteException {
        
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
    public List<RoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) throws RemoteException {
        System.out.println("Tessssssssstttttttttttjejeejejejejejejejejejjejejejejejejejjej");
        
        return null;
    }
    
}
