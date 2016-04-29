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

/**
 * A position of the cartracker.
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
            + "ORDER BY cp.road.roadType"),
    @NamedQuery(name="CarPosition.getPositionsOfRide", query = "SELECT cp "
            + "FROM CarPosition cp "
            + "WHERE cp.rideId = :rideId "
            + "ORDER BY cp.id")
})
public class CarPosition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date moment;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double meter;
    private Long rideId;
    private Boolean lastOfRide;
    
    @ManyToOne
    private Road road;
    
    @ManyToOne
    private Cartracker cartracker;
    
    /**
     * Empty constructor.
     * @deprecated only for jpa
     */
    @Deprecated
    public CarPosition(){
        // JPA
    }
    
    /**
     * A position of the cartracker.
     * @param cartracker The cartracker which this is a position for.
     * @param moment The moment on which the cartracker was on the given 
     *      coordinates. Cannot be null.
     * @param xCoordinate The x-coordinate of this position.
     * @param yCoordinate The y-coordinate of this position.
     * @param road The road on which this position was. Cannot be null.
     * @param meter The distance in meters the cartracker movement since the 
     *      last carposition. Cannot be negative.
     * @param rideId The id of the ride this carposition is a part of.
     * @param lastOfRide Whether this carposition is the last of 
     *      the ride or not.
     */
    public CarPosition(Cartracker cartracker, Date moment, Double xCoordinate, 
            Double yCoordinate, Road road, Double meter, Long rideId, 
            Boolean lastOfRide) {
        if(cartracker == null) {
            throw new IllegalArgumentException("cartracker null");
        }
        if(moment == null){
            throw new IllegalArgumentException("timestamp null");
        }
        if(road == null) {
            throw new IllegalArgumentException("road null");
        }
        if(meter < 0) {
            throw new IllegalArgumentException("km negative");
        }
        this.cartracker = cartracker;
        this.moment = new Date(moment.getTime());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.road = road;
        this.meter = meter;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMoment() {
        return new Date(this.moment.getTime());
    }

    public void setMoment(Date moment) {
        this.moment = new Date(moment.getTime());
    }

    public Cartracker getCartracker() {
        return this.cartracker;
    }

    public void setCartracker(Cartracker cartracker) {
        this.cartracker = cartracker;
    }

    public Double getxCoordinate() {
        return this.xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return this.yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Road getRoad() {
        return this.road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Double getMeter() {
        return this.meter;
    }

    public void setMeter(Double meter) {
        this.meter = meter;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Boolean getLastOfRide() {
        return lastOfRide;
    }

    public void setLastOfRide(Boolean lastOfRide) {
        this.lastOfRide = lastOfRide;
    }
}
