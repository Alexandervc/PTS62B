/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.PreprocessCarpositionDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.Coordinate;
import domain.PreprocessCarposition;
import domain.Road;
import domain.RoadType;
import dto.RoadUsage;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import service.ForeignRideService;
import service.TotalPriceService;
import service.jms.SendForeignRideBean;
import service.jms.SendMissingCarpositions;
import service.rest.clients.ForeignCountryRideClient;

/**
 * Test for carpositionManager.
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
    private RoadUsageManager roadUsageManager;

    @Mock
    @Produces
    private ForeignRideService foreignRideService;

    @Mock
    @Produces
    private PreprocessCarpositionDao preprocessCpDao;

    @Mock
    @Produces
    private SendMissingCarpositions jmsSender;

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
    private ForeignCountryRideClient foreignCountryRideClient;

    @Mock
    @Produces
    private TotalPriceService totalPriceService;

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
    private Boolean firstOfRide;

    private CarPosition carPositionFirst;
    private CarPosition carPositionLast;
    private PreprocessCarposition precarPositionFirst;
    private PreprocessCarposition precarPositionLast;

    private List<PreprocessCarposition> preCarpositions;
    private List<CarPosition> carpositions;
    private List<CarPosition> foreightcarpositions;

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
        this.firstOfRide = true;

        this.foreignRideId = 2L;
        this.foreignNotLastLastOfRide = false;
        this.foreignLastLastOfRide = true;

        this.carPositionFirst = new CarPosition(this.cartracker, this.moment,
                this.coordinate, road, this.meters,
                this.rideId, null, this.lastOfRide, 0L);
        this.carPositionLast = new CarPosition(this.cartracker, this.moment,
                this.coordinate, road, this.meters,
                this.rideId, null, true, 1L);
        this.precarPositionFirst = new PreprocessCarposition(this.cartrackerId,
                this.moment, this.coordinate, road.getName(), this.meters,
                this.rideId, null, this.lastOfRide, this.firstOfRide, 0L);
        this.precarPositionLast = new PreprocessCarposition(this.cartrackerId,
                this.moment, this.coordinate, road.getName(), this.meters,
                this.rideId, null, true, false, 1L);
        this.foreignNotLastCarPosition = new CarPosition(this.foreignCartracker,
                this.moment, this.coordinate, road,
                this.meters, null, this.foreignRideId,
                this.foreignNotLastLastOfRide, 0L);
        this.foreignLastCarPosition = new CarPosition(this.foreignCartracker,
                this.moment, this.coordinate, road,
                this.meters, null, this.foreignRideId,
                this.foreignLastLastOfRide, 1L);
        this.preCarpositions = new ArrayList<>();
        this.preCarpositions.add(this.precarPositionFirst);
        this.preCarpositions.add(this.precarPositionLast);

        this.carpositions = new ArrayList<>();
        this.carpositions.add(this.carPositionFirst);
        this.carpositions.add(this.carPositionLast);

        this.foreightcarpositions = new ArrayList<>();
        this.foreightcarpositions.add(this.foreignNotLastCarPosition);
        this.foreightcarpositions.add(this.foreignLastCarPosition);
    }

    @Test
    public void processPreprocesCarPositionShouldCallCreate() {
        Road temp = new Road(this.roadName, RoadType.A);
        // Define when
        when(this.roadDao.findRoadByName(this.roadName)).thenReturn(temp);
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        // Call method
        this.carPositionManager.processCarPosition(this.cartrackerId,
                this.moment, this.coordinate, this.roadName,
                this.meters, this.rideId, null, this.lastOfRide,
                this.firstOfRide, 0L);

        // Verify
        verify(this.preprocessCpDao)
                .create(argThat(new IsSamePreprocessCarposition(
                        this.precarPositionFirst)));

    }

    @Test
    public void processCompleteRideShouldCallCreate() {
        Road temp = new Road(this.roadName, RoadType.A);
        // Define when
        when(this.roadDao.findRoadByName(this.roadName)).thenReturn(temp);
        when(this.cartrackerDao.find(this.cartrackerId))
                .thenReturn(this.cartracker);
        when(this.preprocessCpDao.getPositionsOfRide(this.rideId,
                this.cartrackerId)).thenReturn(this.preCarpositions);
        // Call method
        this.carPositionManager.checkIfRideIsCompleet(cartrackerId, rideId);

        // Verify
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(
                        this.carPositionFirst)));
        verify(this.carPositionDao)
                .create(argThat(new IsSameCarposition(
                        this.carPositionLast)));

    }

    @Test
    public void processForeignRide() {
        List<RoadUsage> temp = this.roadUsageManager
                .convertToRoadUsages(this.foreightcarpositions);
        String countryCodeTo = this.foreignCartrackerId
                .substring(0, 2);
        String countryCodeFrom = this.cartrackerId
                .substring(0, 2);
        // TODO road
        when(this.roadDao.findAllInternal()).thenReturn(this.roads);
        when(this.totalPriceService.getTotalPrice(temp)).thenReturn(45.00);

        
        // Call method
        this.carPositionManager.processRideForForeignCountry(
                this.foreightcarpositions,
                this.foreignCartrackerId);

        // Verify
        verify(this.foreignRideService)
                .sendForeignRide(this.foreignCartrackerId, 45.00,
                        this.foreightcarpositions, countryCodeTo, 
                        countryCodeFrom);

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
                    .equals(other.getCoordinate())
                    && this.carPosition.getSerialNumber()
                    .equals(other.getSerialNumber());
        }
    }

    /**
     * Matcher for carposition.
     */
    private class IsSamePreprocessCarposition extends ArgumentMatcher<PreprocessCarposition> {

        private final PreprocessCarposition carPosition;

        public IsSamePreprocessCarposition(PreprocessCarposition carPosition) {
            this.carPosition = carPosition;
        }

        @Override
        public boolean matches(Object argument) {
            PreprocessCarposition other = (PreprocessCarposition) argument;
            return this.carPosition.getCartrackerid()
                    .equals(other.getCartrackerid())
                    && this.carPosition.getMeter()
                    .equals(other.getMeter())
                    && this.carPosition.getMoment()
                    .equals(other.getMoment())
                    // TODO road juiste equals
                    && this.carPosition.getRoadName()
                    .equals(other.getRoadName())
                    && this.carPosition.getCoordinate()
                    .equals(other.getCoordinate())
                    && this.carPosition.getSerialNumber()
                    .equals(other.getSerialNumber());
        }
    }
}
