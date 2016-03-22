/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import domain.Bill;
import domain.Rate;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.IRoadUsage;
import service.RadService;

/**
 *
 * @author Alexander
 */
@Named
@RequestScoped
public class BillBean {
    @EJB
    private RadService service;
    
    private Bill bill;
    
    /*public BillBean() {
        generateBill();
    }*/
    /*
    @PostConstruct
    public void start() {
        generateBill();
    }*/
    
    public Bill getBill() {
        if(this.bill == null) {
            generateBill();
        }
        return bill;
    }
    
    public void generateBill() {
        this.bill = service.generateRoadUsages(1L, new Date(), new Date());
    }
    
    public double getRate(IRoadUsage roadUsage) {
        return this.service.getRate(roadUsage.getRoadType()).getRate();
    }
    
    public double getPrice(IRoadUsage roadUsage) {
        return roadUsage.getKm() * this.service.getRate(roadUsage.getRoadType()).getRate();
    }
    
    /*
    public List<IRoadUsage> getRoadUsages() {
        return service.generateRoadUsages(1L, new Date(), new Date());
    }
    */
}
