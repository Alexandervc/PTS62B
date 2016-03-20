/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Singleton
@Startup
@DependsOn("RmiServer")
public class RmiClient {

    private String ipAdressServer = "localhost";
    private int portnumber = 1099;

    private String bindingName = "RAD_Service";
    private Registry registry;

    private IRadService radService;

    @PostConstruct
    private void start() {
        try {
            this.registry = LocateRegistry.getRegistry(ipAdressServer, portnumber);
            if (this.registry != null) {
                try {
                    radService = (IRadService) this.registry.lookup(bindingName);

                } catch (RemoteException ex) {
                    System.out.println("Client: Cannot bind radService");
                    System.out.println("Client: RemoteException: " + ex.getMessage());
                    radService = null;
                } catch (NotBoundException ex) {
                    System.out.println("Client: Cannot bind radService");
                    System.out.println("Client: NotBoundException: " + ex.getMessage());
                    radService = null;
                }

            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }
    }
}
