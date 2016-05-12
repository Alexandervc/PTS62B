/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Car class.
 * @author Linda.
 */
@Entity
public class Car implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String cartrackerId;
    
    @Enumerated(EnumType.STRING)
    private FuelType fuel;
    
    @ManyToOne
    private Person owner;
    
    /**
     * Empty constructor.
     *
     * @deprecated contructor for JPA.
     */
    @Deprecated
    public Car(){
        // Empty for JPA.
    }
    
    /**
     * Contructor Car.
     * @param owner type Person.
     * @param cartracker Long.
     * @param fuel type FuelType.
     */
    public Car(Person owner, String cartracker, FuelType fuel){
        this.owner = owner;
        this.owner.addCar(this);
        this.cartrackerId = cartracker;
        this.fuel = fuel;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartrackerId() {
        return this.cartrackerId;
    }
    
    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }
    
    public FuelType getFuel() {
        return this.fuel;
    }
    
    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }
    
     public Person getOwner() {
        return this.owner;
    }
    
    public void setOwner(Person person) {
        this.owner = person;
    }
}
