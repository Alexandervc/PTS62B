/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import domain.RoadType;
import java.io.Serializable;

/**
 * Helper class to store a usage of a road during a given period.
 * @author Alexander
 */
public class RoadUsage implements Serializable{
    private static final Double kmToMeter = 1000.0;
    
    private String roadName;
    private RoadType roadType;
    private Double km;
    
    /**
     * Helper class to store a usage of a road during a given period.
     * @param roadName The name of the road which this roadUsage is about.
     *      Cannot be null or empty.
     * @param type The type of the road which this roadUsage is about.
     * @param km The distance in km's that is driven on the given road.
     */
    public RoadUsage(String roadName, RoadType type, Double km) {
        if(roadName == null || roadName.isEmpty()) {
            throw new IllegalArgumentException("roadName null or empty");
        }
        this.roadName = roadName;
        this.roadType = type;
        this.km = km;
    }

    public Double getKm() {
        return this.km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getRoadName() {
        return this.roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public RoadType getRoadType() {
        return this.roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }
    
    /**
     * Add the given meter to this km.
     * @param meter The distance in meter to add.
     */
    public void addMeter(Double meter) {
        this.km += meter / kmToMeter;
    }
}
