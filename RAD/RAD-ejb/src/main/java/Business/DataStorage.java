package Business;

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
        service.test();
    }

    public DataStorage() {
        
    }
}
