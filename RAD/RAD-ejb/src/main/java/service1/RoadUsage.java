/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service1;

import domain1.RoadType;
import java.io.Serializable;

/**
 *
 * @author Alexander.
 */
public class RoadUsage implements Serializable, Comparable<RoadUsage> {
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    
    /**
     * Contructor RoadUsage.
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
     * Contructor RoadUsage.
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
     * Getter Km.
     * @return km in Double.
     */
    public Double getKm() {
        return km;
    }

    /**
     * Getter RoadName.
     * @return RoadName in String.
     */
    public String getRoadName() {
        return roadName;
    }

    /**
     * Getter RoadType.
     * @return RoadType Type RoadType.
     */
    public RoadType getRoadType() {
        return roadType;
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
