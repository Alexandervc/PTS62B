/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.RoadType;
import java.io.Serializable;

/**
 *
 * @author Alexander
 */
public class RoadUsage implements IRoadUsage, Serializable{
    private String name;
    private String roadName;
    private RoadType roadType;
    private Double km;
    
    public RoadUsage(String name, String roadName, RoadType type, Double km) {
        this.name = name;
        this.roadName = roadName;
        this.roadType = type;
        this.km = km;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    
}
