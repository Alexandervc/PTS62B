/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import dao.CartrackerDao;
import dao.PreprocessCarpositionDao;
import dao.RoadDao;
import domain.CarPosition;
import domain.Cartracker;
import domain.ForeignCountryRideIdGen;
import domain.Road;
import domain.Coordinate;
import domain.PreprocessCarposition;
import dto.RoadUsage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import service.ForeignRideService;
import service.TotalPriceService;
import service.jms.SendMissingCarpositions;
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
    private PreprocessCarpositionDao preprocessCpDao;

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

    @Inject
    private SendMissingCarpositions jmsSender;

    @Resource
    private ManagedThreadFactory threadFactory;

    /**
     * Start timer class with @Schedule.
     */
    @Schedule(minute = "*/5", hour="*")
    public void startTimer() {
        Thread thread = this.threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                LOGGER.log(Level.INFO, 
                        "Start search for missing serialnumbers");
                searchForMissingPositions();
            }
        });
        thread.start();
    }

    /**
     * Process the given information of a CarPosition.
     *
     * @param cartrackerId The unique identifier of a cartracker. Cannot be null
     * or empty.
     * @param moment The moment in which the cartracker was at the given
     * coordinates.
     * @param coordinate The coordinate of this carposition.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since the
     * last carPosition.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId The id of the foreign country ride this
     * carposition is a part of.
     * @param lastOfRide Whether this carposition is the last of the ride or
     * not.
     * @param firstOfRide Whether this carposition is first of the ride or not.
     * @param serialNumber serial number from simulator.
     */
    public void processCarPosition(String cartrackerId, Date moment,
            Coordinate coordinate, String roadName,
            Double meter, Integer rideId, Long foreignCountryRideId,
            Boolean lastOfRide, Boolean firstOfRide, Long serialNumber) {
        
        try {
            // save to preprocessCpDao
            this.saveCarPosition(cartrackerId, moment, coordinate,
                    roadName, meter, rideId, foreignCountryRideId, lastOfRide,
                    firstOfRide, serialNumber);

        } catch (IllegalArgumentException iaEx) {
            LOGGER.log(Level.SEVERE, null, iaEx);
        }
    }

    /**
     * Stores the car positions into the database and creates a
     * ForeignCountryRide in RAD.
     *
     * @param carPositions The car positions of the ForeignCountryRide.
     * @param foreignCountryRideId The id of the ForeignCountryRide, this is
     * equal to the RideId of the CarPositions.
     * @param totalPrice The total price of the ForeignCountryRide.
     */
    public void processForeignCarRide(
            List<CarPosition> carPositions,
            Long foreignCountryRideId,
            double totalPrice) {

        // Save the CarPositions.
        for (CarPosition carPosition : carPositions) {
            this.carPositionDao.create(carPosition);
        }

        this.foreignCountryRideClient
                .addForeignCountryRide(foreignCountryRideId, totalPrice);

    }

    /**
     * Search for missing positions in database.
     */
    public void searchForMissingPositions() {
        Map<String, List<Long>> hash = this.preprocessCpDao
                .searchForMissingNumbers();
        if (!hash.isEmpty()) {
            for (Map.Entry<String, List<Long>> entry : hash.entrySet()) {
                // Get cartracker id
                String cartrackerid = entry.getKey();

                //Get serialnumbers
                List<Long> serialnumbers = entry.getValue();

                // send cartrackerid and serialnumbers to ASS
                this.jmsSender.sendMissingNumbers(cartrackerid, serialnumbers);
            }
        }
        this.extraSearchForCompleteRides();
    }
    
    public void extraSearchForCompleteRides(){
        List<String> cartrackers = this.preprocessCpDao.getAllCartrackers();
        for(String s : cartrackers){
            List<Integer> rideids = this.preprocessCpDao
                    .getAllRideIdFromCartracker(s);
            for(Integer l : rideids){
                this.checkIfRideIsCompleet(s, l);
            }
        }
    }

    public void processRideForForeignCountry(List<CarPosition> carPositions,
            String cartrackerId) {
        if (carPositions != null) {
            // convert PreprocessCarposition to Carpositions;

            // Get countryCode
            String countryCodeTo = cartrackerId
                    .substring(0, COUNTRYCODE_LENGTH);

            // If foreign and last of ride
            if (!MY_COUNTRYCODE.equals(countryCodeTo)) {

                // Check if all rides are in list
                List<RoadUsage> roadUsages = this.roadUsageManager
                        .convertToRoadUsages(carPositions);

                // Get total price
                double totalPrice = this.totalPriceService
                        .getTotalPrice(roadUsages);

                // Send
                this.foreignRideService.sendForeignRide(cartrackerId,
                        totalPrice, carPositions, countryCodeTo,
                        MY_COUNTRYCODE);
            }
        }
    }

    /**
     * Save the given information into a CarPosition.
     *
     * @param cartrackerId The unique identifier of a cartracker.
     * @param moment The moment in which the cartracker was at the given
     * coordinates.
     * @param coordinate The coordinate of this carpostion.
     * @param roadName The name of the road on which the cartracker was.
     * @param meter The number of meters the cartracker has measured since the
     * last carPosition.
     * @param rideId The id of the ride this carposition is a part of.
     * @param foreignCountryRideId The id of the foreign country ride this
     * carposition is a part of.
     * @param lastOfRide Whether this carposition is the last of the ride or
     * not.
     * @param firstOfRide Whether this carposition is first of the ride or not.
     * @param serialNumber serial number from simulator.
     */
    private void saveCarPosition(String cartrackerId, Date moment,
            Coordinate coordinate, String roadName, Double meter,
            Integer rideId, Long foreignCountryRideId, Boolean lastOfRide,
            Boolean firstOfRide, Long serialNumber) {

        CarPosition cp = this.carPositionDao.findBySerialnumber(serialNumber,
                cartrackerId);
        if (cp == null) {
            // Check if position already exists
            PreprocessCarposition tempCp = null;
            tempCp = this.preprocessCpDao.findBySerialnumber(serialNumber,
                    cartrackerId);
            if (tempCp == null) {

                // TODO road anders
                List<Road> roads = this.roadDao.findAllInternal();
                Random random = new SecureRandom();
                Road road = roads.get(random.nextInt(roads.size()));

                // Make carPosition
                tempCp = new PreprocessCarposition(cartrackerId, moment, coordinate,
                        road.getName(), meter, rideId, foreignCountryRideId,
                        lastOfRide, firstOfRide, serialNumber);

                this.preprocessCpDao.create(tempCp);
            }
            // check if ride is compleet
            this.checkIfRideIsCompleet(cartrackerId, rideId);
        }
    }

    public void checkIfRideIsCompleet(String cartrackerId, Integer rideId) {
        List<PreprocessCarposition> positions;

        PreprocessCarposition cpLast = null;
        PreprocessCarposition cpFirst = null;

        //Get all positions for this cartracker and rideId.
        positions = this.preprocessCpDao
                .getPositionsOfRide(rideId, cartrackerId);

        // Find first and last position.
        for (PreprocessCarposition cp : positions) {
            if (cp.getFirstOfRide()) {
                cpFirst = cp;
            } else if (cp.getLastOfRide()) {
                cpLast = cp;
            }
        }
        
        // If first and last position are found.
        if (cpFirst != null && cpLast != null) {
            positions = this.preprocessCpDao
                .getPositionsOfRide(rideId, cartrackerId);
            // Check if list-Size is complete.
            int completeSize = (cpLast.getSerialNumber()
                    .intValue() + 1) - cpFirst.getSerialNumber().intValue();
            Boolean complete = completeSize == positions.size();
            LOGGER.log(Level.INFO, "complete size: " + completeSize);
            LOGGER.log(Level.INFO, "positions size: " + positions.size());
            
            // If complete stop timer and send carPosition list for check
            // foreign country ride.
            if (complete) {
                // save carpositions to carpositionsDb.
                List<CarPosition> cp = this.
                        processCompleteListOfCarpositions(positions);

                // Check for ForeignCountry
                LOGGER.log(Level.INFO, "check foreign country");
                this.processRideForForeignCountry(cp, cartrackerId);
            }
        }
    }

    public List<CarPosition> processCompleteListOfCarpositions(
            List<PreprocessCarposition> positions) {
        LOGGER.log(Level.INFO, "proces complete carposition rideid");
        List<CarPosition> temp = new ArrayList<>();

        Road road = null;

        for (PreprocessCarposition pcp : positions) {
            // remove from database PreprocessCarposition
            this.preprocessCpDao.remove(pcp);

            // convert preprocessCarposition to Carposition
            road = this.roadDao.findRoadByName(pcp.getRoadName());
            Cartracker car = this.findCartracker(pcp.getCartrackerid());

            CarPosition cp = new CarPosition(car,
                    pcp.getMoment(), pcp.getCoordinate(), road,
                    pcp.getMeter(), pcp.getRideId(),
                    pcp.getForeignCountryRideId(), pcp.getLastOfRide(),
                    pcp.getSerialNumber());
            temp.add(cp);

            // Save in database Carposition
            this.carPositionDao.create(cp);
        }
        return temp;
    }

    /**
     * Find a cartracker by it's id. If it does not exist, create a new
     * Cartracker.
     *
     * @param cartrackerId the cartracker's id.
     * @return The found or created cartracker object.
     */
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
     *
     * @return The next ride id.
     */
    public Long getNextRideIdOfCountryCode() {
        ForeignCountryRideIdGen foreignCountryRideId
                = new ForeignCountryRideIdGen();
        this.em.persist(foreignCountryRideId);

        return foreignCountryRideId.getId();
    }

    /**
     * Get the coordinates in the given month and year for the given
     * cartrackerId.
     *
     * @param month The month to get the coordinates for.
     * @param year The year to get the coordinates for.
     * @param cartrackerId The cartracker to get the coordinates for.
     * @return A list of coordinates.
     */
    public List<Coordinate> getCoordinates(int month, int year,
            String cartrackerId) {
        return this.carPositionDao.getCoordinates(month, year, cartrackerId);
    }    
}
