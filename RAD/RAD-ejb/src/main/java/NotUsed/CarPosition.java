package notUsed;

import java.util.Date;

/**
 *
 * @author Linda
 */
public class CarPosition {    
    private Long id;
    private Date moment;
    private double xCoordinate;
    private double yCoordinate;
    private Road road;
    private double km;
    
    public CarPosition(Long id, Date moment, double xCoordinate, double yCoordinate, Road road, double km) {
        this.id = id;
        this.moment = moment;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.road = road;
        this.km = km;
    }
}
