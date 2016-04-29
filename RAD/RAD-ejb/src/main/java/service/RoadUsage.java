/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.RoadType;
import java.io.Serializable;

/**
 * Class RoadUsage.
 * @author Alexander.
 */
public class RoadUsage implements Serializable, Comparable<RoadUsage> {
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    private Long foreignCountryRideId;

    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param km in Double.
     */
    public RoadUsage(String roadName, String type, Double km) {
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = km;
    }
    
    /**
     * Instantiates the RoadUsage class without a ForeignCountryRideId.
     * @param roadName String.
     * @param type Type RoadType.
     * @param km in Double.
     */
    public RoadUsage(String roadName, RoadType type, Double km) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = km;
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
            String type,
            Double km,
            Long foreignCountryRideId) {
        this.roadName = roadName;
        this.roadType = Enum.valueOf(RoadType.class, type);
        this.km = km;
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
    }

    /**
     * Getter Km.
     * @return km in Double.
     */
    public Double getKm() {
        return this.km;
    }

    /**
     * Getter RoadName.
     * @return RoadName in String.
     */
    public String getRoadName() {
        return this.roadName;
    }

    /**
     * Getter RoadType.
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

    /**
     * Compares RoadName from two RoadUsages.
     * @param t Type RoadUsage.
     * @return integer.
     */
    @Override
    public int compareTo(RoadUsage t) {
        return this.getRoadName().compareTo(t.getRoadName());
    }
}
