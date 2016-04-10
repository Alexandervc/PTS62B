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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Alexander
 */
@Entity
@NamedQueries({
    @NamedQuery(name="CarPosition.getPositionsBetween", query = "SELECT cp "
            + "FROM CarPosition cp "
            + "WHERE "
//            + "cp.moment >= :begin "
  //          + "AND cp.moment <= :end "
    //        + "AND "
            + "cp.cartracker.id = :cartrackerId "
            + "ORDER BY cp.road.roadType")
})
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
    
    @ManyToOne
    private Cartracker cartracker;
    
    @Deprecated
    public CarPosition(){
        
    }
    
    /**
     * 
     * @param cartracker
     * @param moment cannot be null
     * @param xCoordinate
     * @param yCoordinate
     * @param road cannot be null
     * @param km cannto be negative
     */
    public CarPosition(Cartracker cartracker, Date moment, Double xCoordinate, 
            Double yCoordinate, Road road, Double km) {
        if(cartracker == null) {
            throw new IllegalArgumentException("cartracker null");
        }
        if(moment == null){
            throw new IllegalArgumentException("timestamp null");
        }
        if(road == null) {
            throw new IllegalArgumentException("road null");
        }
        if(km < 0) {
            throw new IllegalArgumentException("km negative");
        }
        this.cartracker = cartracker;
        this.moment = new Date(moment.getTime());
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

    public Date getMoment() {
        return new Date(moment.getTime());
    }

    public void setMoment(Date moment) {
        this.moment = new Date(moment.getTime());
    }

    public Cartracker getCartracker() {
        return cartracker;
    }

    public void setCartracker(Cartracker cartracker) {
        this.cartracker = cartracker;
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
