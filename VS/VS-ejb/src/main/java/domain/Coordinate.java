/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Helper class for coordinates.
 * @author Alexander
 */
@Embeddable
public class Coordinate implements Serializable {
    private Double lat;
    private Double lng;
    
    /**
     * Helper class for coordinates.
     * @param lat xCoordinate.
     * @param lng yCoordinate.
     */
    public Coordinate(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    /**
     * Empty constructor for converting to and from json.
     * @deprecated
     */
    @Deprecated
    public Coordinate(){
        // To convert to and from json
    }

    public Double getLat() {
        return this.lat;
    }
    
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Coordinate)) {
            return false;
        }
        Coordinate other = (Coordinate) o;
        return this.getLat().equals(other.getLat())
                && this.getLng().equals(other.getLng());
    }
}
