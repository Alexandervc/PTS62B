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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Preprocess class.
 * @author Linda.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PreprocessCarposition.getPositionsOfRide",
            query = "SELECT cp "
            + "FROM PreprocessCarposition cp "
            + "WHERE cp.rideId = :rideId "
            + " AND cp.cartrackerid = :cartrackerId "
            + " ORDER BY cp.moment, cp.serialNumber"),
    @NamedQuery(name = "PreprocessCarposition.getPositionsWithSerialnumber",
            query = "SELECT cp FROM PreprocessCarposition cp WHERE "
            + "cp.serialNumber = :serialnumber"
            + " AND cp.cartrackerid = :cartrackerId"),
    @NamedQuery(name = "PreprocessCarposition.getPositionByCartracker",
            query = "SELECT cp FROM PreprocessCarposition cp WHERE "
            + " cp.cartrackerid = :cartrackerId"),
    @NamedQuery(name = "PreprocessCarposition.getMissingSerialNumbers",
            query = "SELECT cp FROM PreprocessCarposition cp WHERE "
            + " cp.cartrackerid = :cartrackerId"),
    @NamedQuery(name = "PreprocessCarposition.getCartrackerIds",
            query = "SELECT cp.cartrackerid FROM PreprocessCarposition cp "
                    + "GROUP BY cp.cartrackerid"),
    @NamedQuery(name = "PreprocessCarposition.getRideids",
            query = "SELECT cp.rideId FROM PreprocessCarposition cp "
                    + " WHERE cp.cartrackerid = :cartrackerId " 
                    + "GROUP BY cp.rideId")
})
public class PreprocessCarposition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date moment;

    @Embedded
    private Coordinate coordinate;

    private Double meter;
    private Integer rideId;
    private Long foreignCountryRideId;

    private Boolean lastOfRide;
    private Boolean firstOfRide;

    private String roadName;

    private String cartrackerid;

    private Long serialNumber;

    /**
     * Empty constructor.
     *
     * @deprecated only for jpa
     */
    @Deprecated
    public PreprocessCarposition() {
        // JPA
    }

    /**
     * A position of the cartracker.
     *
     * @param cartracker The cartrackerid which this is a position for.
     * @param moment The moment on which the cartracker was on the given
     * coordinates. Cannot be null.
     * @param coordinate The coordinate of this carposition.
     * @param roadName The roadName on which this position was. Cannot be null.
     * @param meter The distance in meters the cartracker movement since the
     * last carposition. Cannot be negative.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId The id of the foreign ride this carposition
     * is part of.
     * @param lastOfRide Whether this carposition is the last of the ride or
     * not.
     * @param firstOfRide Whether this carposition is first of the ride or not.
     * @param serialNumber serial number from simulator.
     */
    public PreprocessCarposition(String cartracker, Date moment,
            Coordinate coordinate, String roadName, Double meter, Integer rideId,
            Long foreignCountryRideId, Boolean lastOfRide, Boolean firstOfRide,
            Long serialNumber) {
        if (cartracker.isEmpty()) {
            throw new IllegalArgumentException("cartracker null");
        }
        if (moment == null) {
            throw new IllegalArgumentException("timestamp null");
        }
        if (roadName.isEmpty()) {
            throw new IllegalArgumentException("roadName null");
        }
        if (meter < 0) {
            throw new IllegalArgumentException("km negative");
        }
        this.cartrackerid = cartracker;
        this.moment = new Date(moment.getTime());
        this.coordinate = coordinate;
        this.roadName = roadName;
        this.meter = meter;
        this.rideId = rideId;
        this.foreignCountryRideId = foreignCountryRideId;
        this.lastOfRide = lastOfRide;
        this.firstOfRide = firstOfRide;
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

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCartrackerid() {
        return cartrackerid;
    }

    public void setCartrackerid(String cartrackerid) {
        this.cartrackerid = cartrackerid;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
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

    public Boolean getFirstOfRide() {
        return firstOfRide;
    }

    public void setFirstOfRide(Boolean firstOfRide) {
        this.firstOfRide = firstOfRide;
    }

    public Long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

}
