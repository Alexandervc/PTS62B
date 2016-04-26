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
    private Person person3;
    
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
     * @param person type Person.
     * @param cartracker Long.
     * @param fuel type FuelType.
     */
    public Car(Person person, String cartracker, FuelType fuel){
        this.person3 = person;
        this.person3.addCar(this);
        this.cartrackerId = cartracker;
        this.fuel = fuel;
    }

    /**
     * Getter Id.
     * @return Id Long.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter Id.
     * @param id Long. 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter Cartrackerid.
     * @return cartrackerid Long.
     */
    public String getCartrackerId() {
        return this.cartrackerId;
    }
    
    /**
     * Setter cartrackerId.
     * @param cartrackerId Long. 
     */
    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }
    
    /**
     * Getter FuelType.
     * @return FuelType.
     */
    public FuelType getFuel() {
        return this.fuel;
    }
    /**
     * Setter FuelType.
     * @param fuel type FuelType.
     */
    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }
    
    /**
     * Getter Person of Car.
     * @return type Person.
     */
     public Person getPerson3() {
        return this.person3;
    }
     
     /**
      * Setter Person.
      * @param person type Person. 
      */
    public void setPerson3(Person person) {
        this.person3 = person;
    }
    
}
