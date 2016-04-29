/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import business.CarPositionManager;
import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

/**
 *
 * @author Alexander
 */
@RunWith(CdiRunner.class)
public class CarPositionManagerTest {
    @Inject
    private CarPositionManager carPositionManager;
    
    @Mock
    @Produces
    private CartrackerDao cartrackerDao;
    
    @Mock
    @Produces
    private RoadDao roadDao;
    
    @Mock
    @Produces
    private CarPositionDao carPositionDao;
    
    private String cartrackerId;
    private Cartracker cartracker;
    
    private String roadName;
    private List<Road> roads;
    
    private Date moment;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double meters;
    
    private CarPosition carPosition;
    
    /**
     * Matcher for carposition.
     */
    private class IsSameCarposition extends ArgumentMatcher<CarPosition> {
        private final CarPosition carPosition;
        
        public IsSameCarposition(CarPosition carPosition) {
            this.carPosition = carPosition;
        }

        @Override
        public boolean matches(Object argument) {
            CarPosition other = (CarPosition) argument;
            return this.carPosition.getCartracker()
                        .equals(other.getCartracker())
                && this.carPosition.getMeter()
                        .equals(other.getMeter())
                && this.carPosition.getMoment()
                        .equals(other.getMoment())
                // TODO road juiste equals
                && this.carPosition.getRoad().getName()
                        .equals(other.getRoad().getName())
                && this.carPosition.getxCoordinate()
                        .equals(other.getxCoordinate())
                && this.carPosition.getyCoordinate()
                        .equals(other.getyCoordinate());
        }
    }
    
    @Before
    public void beforeTest() {
        // Declare expected values
        this.cartrackerId = "PT123456789";
        this.cartracker = new Cartracker(this.cartrackerId);
        
        this.roadName = "road";
        Road road = new Road(this.roadName, RoadType.A);
        this.roads = new ArrayList<>();
        this.roads.add(road);
        
        this.moment = new Date();
        this.xCoordinate = 1.0;
        this.yCoordinate = 2.0;
        this.meters = 3.0;
        
        this.carPosition = new CarPosition(this.cartracker, this.moment,
                this.xCoordinate, this.yCoordinate, road, this.meters);
    }
    
    @Test
    public void saveCarPostionShouldCallCreate() {
        // Define when
        when(this.cartrackerDao.find(this.cartrackerId))
                .thenReturn(this.cartracker);
        // TODO road
        when(this.roadDao.findAll()).thenReturn(this.roads);
        
        // Call method
        this.carPositionManager.saveCarPosition(this.cartrackerId, this.moment, 
                this.xCoordinate, this.yCoordinate, this.roadName, this.meters);
        
        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(this.carPosition)));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void saveUnknownCartrackerShouldThrowIaex() {
        // Define when
        when(this.cartrackerDao.find(anyLong())).thenReturn(null);
        
        // Call method
        this.carPositionManager.saveCarPosition(this.cartrackerId, this.moment,
                this.xCoordinate, this.yCoordinate, this.roadName, this.meters);
    }
}
