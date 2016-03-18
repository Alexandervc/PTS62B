/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

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
