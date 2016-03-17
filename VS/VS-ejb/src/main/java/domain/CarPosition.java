/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Alexander
 */
@Entity
public class CarPosition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date moment;
    
    private Double xCoordinate;
    private Double yCoordinate;
    private Double km;
    
    @ManyToOne
    private Road road;
    
    public CarPosition(){
        
    }
    
    /**
     * 
     * @param moment cannot be null
     * @param xCoordinate
     * @param yCoordinate
     * @param road cannot be null
     * @param km cannto be negative
     */
    public CarPosition(Date moment, Double xCoordinate, Double yCoordinate, Road road, Double km) {
        if(moment == null){
            throw new IllegalArgumentException("timestamp null");
        }
        if(road == null) {
            throw new IllegalArgumentException("road null");
        }
        if(km < 0) {
            throw new IllegalArgumentException("km negative");
        }
        this.moment = moment;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.road = road;
        this.km = km;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return moment;
    }

    public void setTimestamp(Date timestamp) {
        this.moment = timestamp;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }
}
