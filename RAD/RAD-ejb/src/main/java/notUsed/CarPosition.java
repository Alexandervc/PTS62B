
package notused;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;

/**
 * CarPostion class.
 * @author Linda.
 */
public class CarPosition {
    
    private Long id;
    private Date moment;
    private double xCoordinate;
    private double yCoordinate;
    private Road road;
    private double km;
    
    /**
     * Contructor.
     * @param id Long.
     * @param moment Date.
     * @param xCoordinate double.
     * @param yCoordinate double.
     * @param road type Road.
     * @param km double.
     */
    public CarPosition(Long id, Date moment, double xCoordinate, 
            double yCoordinate, Road road, double km) {
        this.id = id;
        this.moment = new Date(moment.getTime());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.road = road;
        this.km = km;
    }
}
