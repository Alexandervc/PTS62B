/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Linda
 */
public class RoadUsage implements Serializable {
    
    // fields
    private Long id;
    private String name;
    private RoadType type;
    private double km;

    // constructor
    public RoadUsage(Long id, String name, RoadType type, double km) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.km = km;
    }
    
    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RoadType getType() {
        return type;
    }

    public double getKm() {
        return km;
    }
}
