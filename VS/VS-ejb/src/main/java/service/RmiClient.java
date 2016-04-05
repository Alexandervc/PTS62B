/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Alexander
 */
/*@Singleton
@Startup
@DependsOn("RmiServer")
public class RmiClient {

    private String ipAdressServer = "localhost";
    private int portnumber = 1099;

    // TODO produces!!!!??
    private String bindingname = "VS_MovementService";
    private Registry registry;

    private IMovementService movementService;

    @PostConstruct
    private void start() {
        try {
            this.registry = LocateRegistry.getRegistry(ipAdressServer, 
                    portnumber);
            if (this.registry != null) {
                movementService = (IMovementService) 
                        this.registry.lookup(bindingname);
            }
            List<IRoadUsage> roadUsages = movementService.generateRoadUsages(1L, 
                    new Date(), new Date());
            System.out.println(roadUsages);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RmiClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}*/
