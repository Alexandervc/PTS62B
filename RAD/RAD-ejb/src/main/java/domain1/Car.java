/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain1;

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
@Entity
public class Car implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long cartrackerId;
    
    @Enumerated(EnumType.STRING)
    private FuelType fuel;
    
    @ManyToOne
    private Person person3;
    
    @Deprecated
    public Car(){
        
    }
    
    public Car(Person person, Long cartracker, FuelType fuel){
        this.person3 = person;
       this.person3.addCar(this);
        this.cartrackerId = cartracker;
        this.fuel = fuel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCartrackerId(Long cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public void setFuel(FuelType fuel) {
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
    
     public Person getPerson3() {
        return person3;
    }
     
    public void setPerson3(Person person) {
        this.person3 = person;
    }
}
