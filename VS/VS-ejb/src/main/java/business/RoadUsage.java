/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.RoadType;
import java.io.Serializable;

/**
 *
 * @author Alexander
 */
public class RoadUsage implements Serializable{
    private String roadName;
    private RoadType roadType;
    private Double km;
    
    public RoadUsage(String roadName, RoadType type, Double km) {
        this.roadName = roadName;
        this.roadType = type;
        this.km = km;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }
    
    /**
     * Add the given meter to this km
     * @param meter 
     */
    public void addMeter(Double meter) {
        this.km += (meter / 1000);
    }
}
