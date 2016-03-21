/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Edwin
 */
public class RMI_Client {
        // Set binding name for student administration
    private String bindingName;

    // References to registry and student administration
    private Registry registry = null;
    // Constructor
    public RMI_Client(String ipAddress, int portNumber) {
        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

    }
    
    public IMonitoring getMonitoringClient(String bindingName) {
        if (registry != null) {
            try {
                return (IMonitoring) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: RemoteException: " + ex.getMessage());
                return null;
            } catch (NotBoundException ex) {
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                return null;
            }
        }
        return null;
    }
    
    public VSinterface getVSClient(String bindingName) {
        if (registry != null) {
            try {
                return (VSinterface) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: RemoteException: " + ex.getMessage());
                return null;
            } catch (NotBoundException ex) {
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                return null;
            }
        }
        return null;
    }

}
