/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alexander
 */
public interface IMovementService extends Remote {
    List<IRoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) 
            throws RemoteException;
}
