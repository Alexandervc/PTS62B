package controller;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.PathService;

/**
 * Simulatorbean for simulator application.
 * @author Melanie.
 */
@Named
@RequestScoped
public class SimulatorBean {
    @Inject
    private PathService service;
    
    private List<String> cartrackers;
    private String cartracker;
    private Date date;
    
    /**
     * Genereate file for chosen cartracker and date.
     */
    public void generate() {
        if (this.cartracker != null && !this.cartracker.isEmpty() 
                && this.date != null) {
            this.service.generateFiles(this.cartracker);
        }
    }
    
    /**
     * Get all cartrackers from PathService. 
     * 
     * @return list of cartrackers.
     */
    public List<String> getCartrackers() {
        if (this.cartrackers == null) {
            this.cartrackers = this.service.getCartrackers();
        }
        
        return this.cartrackers;
    }

    public String getCartracker() {
        return this.cartracker;
    }

    public void setCartracker(String cartracker) {
        this.cartracker = cartracker;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}