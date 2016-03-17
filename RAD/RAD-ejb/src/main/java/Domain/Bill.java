/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Linda
 */
@Entity (name = "Bill")
public class Bill implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Transient
    private List<RoadUsage> roadUsages;
    
    @Column(name ="Paid")
    private boolean paid;
    
    @Column(name ="TotalPrice")
    private double totalPrice;
    
    public Bill(){
        this.paid = false;
        this.totalPrice = 13.89;
        this.roadUsages = new ArrayList<RoadUsage>();
    }
    
    public void setRoadUsage(List<RoadUsage> roadUsage) {
        this.roadUsages = roadUsage;
    }
}
