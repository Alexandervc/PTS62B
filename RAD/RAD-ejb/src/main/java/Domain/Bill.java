/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author Linda
 */
@Entity(name = "Bill")
public class Bill implements Serializable {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade= CascadeType.PERSIST)
    private Person person;

    @Transient
    private List<RoadUsage> roadUsages;

    @Column(name = "Paid")
    private boolean paid;

    @Column(name = "TotalPrice")
    private double totalPrice;

    // constructor
    public Bill() {
        this.paid = false;
        this.totalPrice = 13.89;
        this.roadUsages = new ArrayList<RoadUsage>();
        this.person = new Person();
    }

    // getters en setters
    public Long getId() {
        return id;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    public List<RoadUsage> getRoadUsages() {
        return roadUsages;
    }

    public void setRoadUsage(List<RoadUsage> roadUsage) {
        this.roadUsages = roadUsage;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
