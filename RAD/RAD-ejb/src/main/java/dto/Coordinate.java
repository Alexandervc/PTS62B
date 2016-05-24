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
    private Double x;
    private Double y;
    
    /**
     * Helper class for coordinates.
     * @param x xCoordinate.
     * @param y yCoordinate.
     */
    public Coordinate(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Empty constructor for converting to and from json.
     * @deprecated
     */
    @Deprecated
    public Coordinate(){
        // To convert to and from json
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
}
