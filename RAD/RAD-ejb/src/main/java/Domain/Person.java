/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Linda
 */
@Entity (name = "Person")
public class Person implements Serializable {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy="person")
    private List<Bill> bills;
    
    private Long cartracker;

    //constructor
    public Person() {
        bills = new ArrayList<Bill>();
    }
    
    // getters en setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public List<Bill> getBills() {
        return bills;
    }
    
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    
    public Long getCartracker() {
        return cartracker;
    }

    public void setCartracker(Long cartracker) {
        this.cartracker = cartracker;
    }
    
    /**
     * add bill to list bills
     * @param b 
     */
    public void addBill(Bill b){
        this.bills.add(b);
        b.setPerson(this);
    }
}
