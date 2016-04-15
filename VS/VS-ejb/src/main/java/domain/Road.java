/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Represents a road.
 * @author Alexander
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Road.findAll", query = "SELECT r FROM Road r")
})
public class Road implements Serializable, Comparable<Road> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    private String name;
    
    @OneToMany(mappedBy = "road")
    private List<CarPosition> carPositions;
    
    @Enumerated(EnumType.STRING)
    private RoadType roadType;
    
    /**
     * Empty constructor.
     * @deprecated only for jpa
     */
    @Deprecated
    public Road() {
        
    }
    
    /**
     * Represents a road.
     * @param name The name of the road. Cannot be null or empty
     * @param roadType The type of the road.
     */
    public Road(String name, RoadType roadType) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name null");
        }
        this.name = name;
        this.roadType = roadType;
        this.carPositions = new ArrayList<>();
    }

    public RoadType getRoadType() {
        return this.roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public List<CarPosition> getCarPositions() {
        return new ArrayList<>(this.carPositions);
    }

    public void setCarPositions(List<CarPosition> carPositions) {
        this.carPositions = new ArrayList<>(carPositions);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Road)) {
            return false;
        }
        Road other = (Road) o;
        return this.getId().equals(other.getId());
    }

    @Override
    public int compareTo(Road o) {
        return this.getId().compareTo(o.getId());
    }
}
