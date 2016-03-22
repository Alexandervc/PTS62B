package DAO;

import Domain.RoadType;
import Service.RadService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Singleton
@Startup
public class DataStorage {
    @Inject
    private RadService service;
    
    @PostConstruct
    public void onStartup() {
        //service.test();
        service.addRate(1.29, RoadType.A);
        service.addRate(0.89, RoadType.B);
        service.addRate(0.49, RoadType.C);
    }
}
