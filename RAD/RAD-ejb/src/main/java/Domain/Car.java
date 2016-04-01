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
 *
 * @author Linda
 */
@Entity (name = "Car")
public class Car implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long cartrackerId;
    
    @Enumerated(EnumType.STRING)
    private FuelType fuel;
    
    @ManyToOne
    private Person person;
    
    @Deprecated
    public Car(){
        
    }
    
    public Car(Person person, Long cartracker, FuelType fuel){
        this.person = person;
       this.person.addCar(this);
        this.cartrackerId = cartracker;
        this.fuel = fuel;
    }

    public Long getId() {
        return id;
    }

    public Long getCartrackerId() {
        return cartrackerId;
    }

    public FuelType getFuel() {
        return fuel;
    }
    
     public Person getPerson() {
        return person;
    }
     
    public void setPerson(Person person) {
        this.person = person;
    }
}
