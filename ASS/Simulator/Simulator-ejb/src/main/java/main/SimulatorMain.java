package main;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;
import service.PathService;

/**
 * SimulatorMain Class.
 * @author Melanie.
 */
@Singleton
@Startup
public class SimulatorMain {
    @Inject
    private PathService service;

    /**
     * Generate files for roadusage.
     */
    @PostConstruct
    public void main() {
        this.service.generateFiles();
    }    
}
