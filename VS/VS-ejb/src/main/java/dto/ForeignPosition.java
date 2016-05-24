/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.Coordinate;

/**
 * Helper class for sending positions to foreign countries.
 * @author Alexander
 */
public class ForeignPosition implements Comparable<ForeignPosition> {
    private Coordinate coordinate;
    private String datetime;
    
    /**
     * Helper class for sending positions to foreign countries.
     * @param coordinate The coordinate of this carposition.
     * @param datetime The moment on which the car was at this position.
     */
    public ForeignPosition(Coordinate coordinate, String datetime) {
        this.coordinate = coordinate;
        this.datetime = datetime;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    @Override
    public int compareTo(ForeignPosition o) {
        return this.getDatetime().compareTo(o.getDatetime());
    }
}
