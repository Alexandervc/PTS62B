/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.RoadType;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class BillRoadUsage.
 *
 * @author Alexander
 */
public class BillRoadUsage implements Serializable, Comparable<BillRoadUsage> {
    
    private static final Logger LOGGER = Logger
            .getLogger(BillRoadUsage.class.getName());
    private static final Double KM_TO_METER = 1000.0;    
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    private Long foreignCountryRideId;
    private Double price;
    private Double rate;

    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     *
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     * 
     * @deprecated contructor.
     */
    @Deprecated
    public BillRoadUsage(String roadName, String type, Double meters) {
        LOGGER.log(Level.WARNING, "use of deprecated RoadUsage constructor");
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = meters / KM_TO_METER;
        this.price = 0.0;
        this.rate = 0.0;
    }

    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     *
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     */
    public BillRoadUsage(String roadName, RoadType type, Double meters) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = meters / KM_TO_METER;
        this.price = 0.0;
        this.rate = 0.0;
    }

    /**
     * Instantiates the RoadUsage class with a ForeignCountryRideId.
     *
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     * @param foreignCountryRideId The id of the foreign country ride.
     * 
     * @deprecated contructor.
     */
    @Deprecated
    public BillRoadUsage(
            String roadName,
            String type,
            Double meters,
            Long foreignCountryRideId) {
        LOGGER.log(Level.WARNING, "use of deprecated RoadUsage with"+
                " foreignCountryRide constructor");
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = meters / KM_TO_METER;
        this.foreignCountryRideId = foreignCountryRideId;
        this.price = 0.0;
        this.rate = 0.0;
    }

    /**
     * Instantiates the RoadUsage class with a ForeignCountryRideId.
     *
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     * @param foreignCountryRideId The id of the foreign country ride.
     */
    public BillRoadUsage(
            String roadName,
            RoadType type,
            Double meters,
            Long foreignCountryRideId) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = meters / KM_TO_METER;
        this.foreignCountryRideId = foreignCountryRideId;
        this.price = 0.0;
        this.rate = 0.0;
    }

    /**
     * Constructor for converting to and from JSON.
     *
     * @deprecated JSON
     */
    @Deprecated
    public BillRoadUsage() {
        // For converting to and from JSON
        this.price = 0.0;
        this.rate = 0.0;
    }

    /**
     * Getter Km.
     *
     * @return km in Double.
     */
    public Double getKm() {
        return this.km;
    }

    /**
     * Getter RoadName.
     *
     * @return RoadName in String.
     */
    public String getRoadName() {
        return this.roadName;
    }

    /**
     * Getter RoadType.
     *
     * @return RoadType Type RoadType.
     */
    public RoadType getRoadType() {
        return this.roadType;
    }

    public Long getForeignCountryRideId() {
        return this.foreignCountryRideId;
    }

    public void setForeignCountryRideId(Long foreignCountryRideId) {
        this.foreignCountryRideId = foreignCountryRideId;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    /**
     * Compares RoadName from two RoadUsages.
     *
     * @param t Type BillRoadUsage.
     * @return integer.
     */
    @Override
    public int compareTo(BillRoadUsage t) {
        return this.getRoadName().compareTo(t.getRoadName());
    }
}
