package service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author Alexander
 */
@Stateless
public class RmiClient {

    private String ipAdressServer = "localhost";
    private int portnumber = 1099;

    // TODO produces!!!!??
    private String bindingname = "VS_MovementService";
    private Registry registry;

    private IMovementService movementService;

    //@PostConstruct
    private void start() {
        try {
            this.registry = LocateRegistry.getRegistry(ipAdressServer, portnumber);
            if (this.registry != null) {
                //movementService = (IMovementService) PortableRemoteObject.narrow(this.registry.lookup(bindingname), IMovementService.class);
                movementService = (IMovementService) this.registry.lookup(bindingname);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<IRoadUsage> generateRoadUsages(Long cartrackerId, Date begin, Date end) throws RemoteException {
        List<IRoadUsage> roadUsages = movementService.generateRoadUsages(cartrackerId, begin, end);
            System.out.println(roadUsages.get(0).getKm());
            return roadUsages;
    }
}
