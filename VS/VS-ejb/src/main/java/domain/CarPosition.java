/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embedded;
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
    @NamedQuery(name="CarPosition.getPositionsOfRide", query = "SELECT cp "
            + "FROM CarPosition cp "
            + "WHERE cp.rideId = :rideId "
            + "ORDER BY cp.moment, cp.serialNumber"),
    @NamedQuery(name="CarPosition.getPositionsOfForeignCountryRide", query = 
            "SELECT cp "
            + "FROM CarPosition cp "
            + "WHERE cp.foreignCountryRideId = :foreignCountryRideId "
            + "ORDER BY cp.moment, cp.serialNumber")
})
public class CarPosition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date moment;
    
    private Double meter;
    private Integer rideId;
    private Long foreignCountryRideId;
    
    private Boolean lastOfRide;
    
    @Embedded
    private Coordinate coordinate;
    
    @ManyToOne
    private Road road;
    
    @ManyToOne
    private Cartracker cartracker;
    
    private Long serialNumber;
    
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
     * @param coordinate The coordinate of this carposition.
     * @param road The road on which this position was. Cannot be null.
     * @param meter The distance in meters the cartracker movement since the 
     *      last carposition. Cannot be negative.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId The id of the foreign ride this carposition 
     *      is part of.
     * @param lastOfRide Whether this carposition is the last of 
     *      the ride or not.
     * @param serialNumber serial number from simulator.
     */
    public CarPosition(Cartracker cartracker, Date moment, 
            Coordinate coordinate, Road road, Double meter, Integer rideId, 
            Long foreignCountryRideId, Boolean lastOfRide, 
            Long serialNumber) {
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
        this.coordinate = coordinate;
        this.road = road;
        this.meter = meter;
        this.rideId = rideId;
        this.foreignCountryRideId = foreignCountryRideId;
        this.lastOfRide = lastOfRide;
        this.serialNumber = serialNumber;
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

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
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

    public Integer getRideId() {
        return this.rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Long getForeignCountryRideId() {
        return this.foreignCountryRideId;
    }

    public void setForeignCountryRideId(Long foreignCountryRideId) {
        this.foreignCountryRideId = foreignCountryRideId;
    }
    
    public Boolean getLastOfRide() {
        return this.lastOfRide;
    }

    public void setLastOfRide(Boolean lastOfRide) {
        this.lastOfRide = lastOfRide;
    }

    public Long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }
}
