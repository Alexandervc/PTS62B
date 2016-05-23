/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.ForeignCountryRideIdGen;
import domain.Road;
import dto.RoadUsage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import service.ForeignRideService;
import service.TotalPriceService;
import service.rest.clients.ForeignCountryRideClient;

/**
 * The manager of carPositions.
 *
 * @author Alexander
 */
@Stateless
public class CarPositionManager {

    private static final Logger LOGGER
            = Logger.getLogger(CarPositionManager.class.getName());

    private static final int COUNTRYCODE_LENGTH = 2;
    private static final String MY_COUNTRYCODE = "PT";
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private RoadUsageManager roadUsageManager;

    @Inject
    private CartrackerDao cartrackerDao;

    @Inject
    private RoadDao roadDao;

    @Inject
    private CarPositionDao carPositionDao;

    @Inject
    private TotalPriceService totalPriceService;

    @Inject
    private ForeignRideService foreignRideService;

    @Inject
    private ForeignCountryRideClient foreignCountryRideClient;
    
    /**
     * Process the given information of a CarPosition.
     * @param cartrackerId The unique identifier of a cartracker. Cannot be null
     *      or empty.
     * @param moment The moment in which the cartracker was at the given
     *      coordinates.
     * @param xCoordinate The x-coordinate of the carPosition.
     * @param yCoordinate The y-coordinate of the carPosition.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since the
     *      last carPosition.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId The id of the foreign country ride this 
     *      carposition is a part of.
     * @param lastOfRide Whether this carposition is the last of the ride or
     *      not.
     */
    public void processCarPosition(String cartrackerId, Date moment,
            Double xCoordinate, Double yCoordinate, String roadName,
            Double meter, Integer rideId, Long foreignCountryRideId, 
            Boolean lastOfRide) {
        try {
            this.saveCarPosition(cartrackerId, moment, xCoordinate, yCoordinate,
                    roadName, meter, rideId, foreignCountryRideId, lastOfRide);

            // Get countryCode
            String countryCode = cartrackerId.substring(0, COUNTRYCODE_LENGTH);

            // If foreign car and last of ride
            if (!MY_COUNTRYCODE.equals(countryCode) && lastOfRide) {
                // Get carpostions and roadusages of ride
                List<CarPosition> carPositions = this.carPositionDao
                        .getPositionsOfRide(rideId);
                List<RoadUsage> roadUsages = this.roadUsageManager
                        .convertToRoadUsages(carPositions);

                // Get total price
                double totalPrice = this.totalPriceService
                        .getTotalPrice(roadUsages);
                
                // Send
                this.foreignRideService.sendForeignRide(cartrackerId,
                        totalPrice, carPositions, countryCode);
            }
        } catch (IllegalArgumentException iaEx) {
            LOGGER.log(Level.SEVERE, null, iaEx);
        }
    }
    
    /**
     * Stores the car positions into the database and creates a 
     * ForeignCountryRide in RAD.
     * @param carPositions The car positions of the ForeignCountryRide.
     * @param foreignCountryRideId The id of the ForeignCountryRide, this is 
     *      equal to the RideId of the CarPositions.
     * @param totalPrice The total price of the ForeignCountryRide.
     */
    public void processForeignCarRide(
            List<CarPosition> carPositions, 
            Long foreignCountryRideId,
            double totalPrice) {
        
        // Save the CarPositions.
        for(CarPosition carPosition : carPositions) {
            this.carPositionDao.create(carPosition);
        }
        
        this.foreignCountryRideClient
                .addForeignCountryRide(foreignCountryRideId, totalPrice);
        
    }

    /**
     * Save the given information into a CarPosition.
     * @param cartrackerId The unique identifier of a cartracker.
     * @param moment The moment in which the cartracker was at the given
     *      coordinates.
     * @param xCoordinate The x-coordinate of the carPosition.
     * @param yCoordinate The y-coordinate of the carPosition.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since the
     *      last carPosition.
     */
    private void saveCarPosition(String cartrackerId, Date moment,
            Double xCoordinate, Double yCoordinate, String roadName,
            Double meter, Integer rideId, Long foreignCountryRideId,
            Boolean lastOfRide) {
        // Find cartracker
        Cartracker cartracker = this.findCartracker(cartrackerId);

        // TODO road anders
        List<Road> roads = this.roadDao.findAll();
        Random random = new SecureRandom();
        Road road = roads.get(random.nextInt(roads.size()));

        // Make carPosition
        CarPosition cp = new CarPosition(cartracker, moment, xCoordinate,
                yCoordinate, road, meter, rideId, foreignCountryRideId,
                lastOfRide);

        this.carPositionDao.create(cp);
    }
    
    public Cartracker findCartracker(String cartrackerId) {
        // Find cartracker
        Cartracker cartracker = this.cartrackerDao.find(cartrackerId);
        if (cartracker == null) {
            // Create cartracker
            this.cartrackerDao.create(new Cartracker(cartrackerId));
            cartracker = this.cartrackerDao.find(cartrackerId);
        }
        
        return cartracker;
    }
    
    /**
     * Gets the next foreign country ride id of a carposition.
     * @return The next ride id.
     */
    public Long getNextRideIdOfCountryCode() {
        // TODO: fix concurrency
        ForeignCountryRideIdGen foreignCountryRideId = new ForeignCountryRideIdGen();
        this.em.persist(foreignCountryRideId);
        
        return foreignCountryRideId.getId();
    }
}
