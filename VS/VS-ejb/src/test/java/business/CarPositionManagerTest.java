/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.Coordinate;
import domain.Road;
import domain.RoadType;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import service.TotalPriceService;
import service.jms.SendForeignRideBean;
import service.rest.clients.ForeignCountryRideClient;

/**
 * Test for carpositionManager.
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
    
    @Mock
    @Produces
    private SendForeignRideBean sendForeignRideBean;
    
    @Mock
    @Produces
    private TotalPriceService radWsService;
    
    @Mock
    @Produces
    private ForeignCountryRideClient foreignCountryRideClient;
    
    // Cartracker
    private String cartrackerId;
    private Cartracker cartracker;
    
    private String roadName;
    private List<Road> roads;
    
    private Date moment;
    private Coordinate coordinate;
    private Double meters;
    private Integer rideId;
    private Boolean lastOfRide;
    
    private CarPosition carPosition;
    
    // Foreign cartracker
    private String foreignCartrackerId;
    private Cartracker foreignCartracker;
    
    private Long foreignRideId;
    private Boolean foreignNotLastLastOfRide;
    private Boolean foreignLastLastOfRide;
    
    private CarPosition foreignNotLastCarPosition;
    private CarPosition foreignLastCarPosition;
    
    @Before
    public void beforeTest() {
        // Declare expected values
        this.cartrackerId = "PT123456789";
        this.cartracker = new Cartracker(this.cartrackerId);
        
        this.foreignCartrackerId = "BE123456789";
        this.foreignCartracker = new Cartracker(this.foreignCartrackerId);
        
        this.roadName = "road";
        Road road = new Road(this.roadName, RoadType.A);
        this.roads = new ArrayList<>();
        this.roads.add(road);
        
        this.moment = new Date();
        this.coordinate = new Coordinate(1.0, 2.0);
        this.meters = 3.0;
        this.rideId = 1;
        this.lastOfRide = false;
        
        this.foreignRideId = 2L;
        this.foreignNotLastLastOfRide = false;
        this.foreignLastLastOfRide = true;
        
        this.carPosition = new CarPosition(this.cartracker, this.moment,
                this.coordinate, road, this.meters,
                this.rideId, null, this.lastOfRide, 0L);        
        this.foreignNotLastCarPosition = new CarPosition(this.foreignCartracker,
                this.moment, this.coordinate, road, 
                this.meters, null, this.foreignRideId, 
                this.foreignNotLastLastOfRide, 0L);
        this.foreignLastCarPosition = new CarPosition(this.foreignCartracker,
                this.moment, this.coordinate, road,
                this.meters, null, this.foreignRideId, 
                this.foreignLastLastOfRide, 0L);
    }
    
    @Test
    public void processCarPositionShouldCallCreate() {
        // Define when
        when(this.cartrackerDao.find(this.cartrackerId))
                .thenReturn(this.cartracker);
        // TODO road
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        
        // Call method
        this.carPositionManager.processCarPosition(this.cartrackerId, 
                this.moment, this.coordinate, this.roadName, 
                this.meters, this.rideId, null, this.lastOfRide, 0L);
        
        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(this.carPosition)));
    }
    
    //@Test
    public void processForeignCarPositionLastShouldCallGetPositions() {
        // Define when
        when(this.cartrackerDao.find(this.foreignCartrackerId))
                .thenReturn(this.foreignCartracker);
        // TODO road
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        
        // Call method
        this.carPositionManager.processCarPosition(this.foreignCartrackerId, 
                this.moment, this.coordinate, this.roadName, 
                this.meters, null, this.foreignRideId, 
                this.foreignLastLastOfRide, 0L);
        
        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(
                        this.foreignLastCarPosition)));
        
        verify(this.carPositionDao)
                .getPositionsOfForeignCountryRide(this.foreignRideId);
        
        // TODO totalPrice + send
    }
    
    @Test
    public void processForeignCarPositionNotLastShouldNotCallGetPositions() {
        // Define when
        when(this.cartrackerDao.find(this.foreignCartrackerId))
                .thenReturn(this.foreignCartracker);
        // TODO road
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        
        // Call method
        this.carPositionManager.processCarPosition(this.foreignCartrackerId, 
                this.moment, this.coordinate, this.roadName, 
                this.meters, null, this.foreignRideId, 
                this.foreignNotLastLastOfRide, 0L);
        
        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(
                        this.foreignNotLastCarPosition)));
        
        verify(this.carPositionDao, times(0))
                .getPositionsOfForeignCountryRide(this.foreignRideId);
        
        // TODO totalPrice + send
    }
    
    @Test
    public void processUnknownCartrackerShouldCreate() {
        /*
        // TODO not testable anymore
        
        // Define when
        when(this.cartrackerDao.find(anyString())).thenReturn(null);
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        
        // Call method
        this.carPositionManager.processCarPosition(this.cartrackerId, 
                this.moment, this.xCoordinate, this.yCoordinate, this.roadName, 
                this.meters);
        
        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(this.carPosition)));
        */
    }
    
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
                && this.carPosition.getCoordinate()
                        .equals(other.getCoordinate());
        }
    }
}
