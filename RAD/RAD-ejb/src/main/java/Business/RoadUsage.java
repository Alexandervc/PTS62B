/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.RoadType;
import java.io.Serializable;
import service.IRoadUsage;

/**
 *
 * @author Alexander.
 */
public class RoadUsage implements IRoadUsage, Serializable, Comparable<RoadUsage> {
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    
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
    @Override
    public Double getKm() {
        return km;
    }

    /**
     * Setter Km.
     * @param km in Double.
     */
    public void setKm(Double km) {
        this.km = km;
    }

    /**
     * Getter RoadName.
     * @return RoadName in String.
     */
    @Override
    public String getRoadName() {
        return roadName;
    }

    /**
     * Setter RoadName.
     * @param roadName in String. 
     */
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    /**
     * Getter RoadType.
     * @return RoadType Type RoadType.
     */
    @Override
    public RoadType getRoadType() {
        return roadType;
    }

    /**
     * Setter RoadType.
     * @param roadType Type RoadType.
     */
    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }
    
    /**
     * Add the given km to this km.
     * @param km in double.
     */
    public void addKm(Double km) {
        this.km += km;
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
