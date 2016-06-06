/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.RoadType;
import java.io.Serializable;

/**
 * Class RoadUsage.
 * @author Alexander
 */
public class RoadUsage implements Serializable, Comparable<RoadUsage> {
    
    private static final Double KM_TO_METER = 1000.0;
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    private Long foreignCountryRideId;

    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     */
    public RoadUsage(String roadName, String type, Double meters) {
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = meters / KM_TO_METER;
    }
    
    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     */
    public RoadUsage(String roadName, RoadType type, Double meters) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = meters / KM_TO_METER;
    }
    
    /**
     * Instantiates the RoadUsage class with a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param meters in Double.
     * @param foreignCountryRideId The id of the foreign country ride.
     * @deprecated String type should be RoadType class.
     */
    @Deprecated
    public RoadUsage(
            String roadName,
            String type,
            Double meters,
            Long foreignCountryRideId) {
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = meters / KM_TO_METER;
        this.foreignCountryRideId = foreignCountryRideId;
    }
    
    /**
     * Instantiates the RoadUsage class with a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param km in Double.
     * @param foreignCountryRideId The id of the foreign country ride.
     */
    public RoadUsage(
            String roadName,
            RoadType type,
            Double km,
            Long foreignCountryRideId) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = km;
        this.foreignCountryRideId = foreignCountryRideId;
    }
    
    /**
     * Constructor for converting to and from JSON.
     * @deprecated JSON
     */
    @Deprecated
    public RoadUsage() {
        // For converting to and from JSON
    }

    public Double getKm() {
        return this.km;
    }
    
    public String getRoadName() {
        return this.roadName;
    }
    
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
    
    /**
     * Add the given meter to this km.
     * @param meter The distance in meter to add.
     */
    public void addMeter(Double meter) {
        this.km += meter / KM_TO_METER;
    }

    @Override
    public int compareTo(RoadUsage other) {
        // Compare by roadType and roadName
        // Foreign_Country_Road on the bottom
        if(this.getRoadType().equals(other.getRoadType())) {
            return this.getRoadName().compareTo(other.getRoadName());
        } else {
            if(this.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
                return 1;
            } else if (other.getRoadType() == RoadType.FOREIGN_COUNTRY_ROAD) {
                return -1;
            } else {
                return this.getRoadType().compareTo(other.getRoadType());
            }
        }
    }
}
