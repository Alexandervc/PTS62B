/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 * Helper class for coordinates.
 * @author Alexander
 */
public class Coordinate {
    private double lat;
    private double lng;
    
    /**
     * Helper class for coordinates.
     * @param lat xCoordinate.
     * @param lng yCoordinate.
     */
    public Coordinate(double lat, double lng) {
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

    public double getLat() {
        return this.lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
