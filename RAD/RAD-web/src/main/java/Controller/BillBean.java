/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import domain.Bill;
import java.text.NumberFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
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
        this.bill = service.generateRoadUsages(this.name, new Date(), new Date());
    }
    
    public String getRate(IRoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.service.getRate(roadUsage.getRoadType()).getRate());
    }
    
    public String getPrice(IRoadUsage roadUsage) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(roadUsage.getKm() * this.service.getRate(roadUsage.getRoadType()).getRate());
    }
    
    public String getTotalPrice() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(this.bill.getTotalPrice());
    }
    
    public String getPersonName() {
        return this.bill.getPerson().getInitials() + " " + this.bill.getPerson().getLastName();
    }
    
    /*
    public List<IRoadUsage> getRoadUsages() {
        return service.generateRoadUsages(1L, new Date(), new Date());
    }
    */
}
