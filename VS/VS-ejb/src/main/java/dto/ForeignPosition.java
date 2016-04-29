/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 * Helper class for sending positions to foreign countries.
 * @author Alexander
 */
public class ForeignPosition {
    private Double x;
    private Double y;
    private String datetime;
    
    /**
     * Helper class for sending positions to foreign countries.
     * @param x The xCoordinate of the position.
     * @param y The yCoordinate of the position.
     * @param datetime The moment on which the car was at this position.
     */
    public ForeignPosition(Double x, Double y, String datetime) {
        this.x = x;
        this.y = y;
        this.datetime = datetime;
    }

    public Double getX() {
        return this.x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    
    
}
