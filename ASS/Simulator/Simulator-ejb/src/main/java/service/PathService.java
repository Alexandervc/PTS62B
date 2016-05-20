package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import model.DirectionInput;
import model.GpsSimulatorInstance;
import model.Leg;
import model.Point;
import service.jms.SendPositionBean;
import simulator.GpsSimulator;
import support.NavUtils;

/**
 * PathService Class.
 *
 * @author Melanie.
 */
@Stateless
public class PathService implements Serializable {    
    private final static String PROJECT_ROOT = 
            "C:\\Proftaak";

    private static final String API_KEY = 
            "AIzaSyCDUV1tIzDx5or4V-wrAsSN9lc8Gvpsz6Y";
    
    private transient BufferedReader reader;

    private List<String> locations;
    private final Map<Long, GpsSimulatorInstance> taskFutures = new HashMap<>();
    private final transient ExecutorService taskExecutor
            = Executors.newSingleThreadExecutor();
    private long instanceCounter = 1;    
    
    private List<String> cartrackers;

    @Inject
    private SendPositionBean sendPositionBean;

    /**
     * Setup location info.
     */
    @PostConstruct
    public void setup() {
        //Setup list of all cartrackers
        this.cartrackers = new ArrayList<>();
        this.cartrackers.add("PT112233444");
        this.cartrackers.add("PT121312141");
        this.cartrackers.add("PT123456789");
        this.cartrackers.add("BE-a5eff926-e3f7-43d5-b62b-5140aa2b962f");
        this.cartrackers.add("LU203647582746");
        this.cartrackers.add("NL123456789");
        
        //Setup locations
        this.locations = new ArrayList<>();
        this.locations.add("R. do Ouro,1150-060 Lisboa,Portugal");
        this.locations.add("R. Alm. Gago Coutinho,Albufeira,Portugal");
        this.locations.add("R. Dr. Faria e Silva 34,Portugal");
        this.locations.add("Entrada da Barca, 7630-340 Zambujeira do Mar, "
                + "7630-734, Portugal");
        this.locations.add("R. de Serpa Pinto,84,7940-172 Cuba,Portugal");
        this.locations.add("Terreiro da Sé,4050-573 Porto,Portugal");
        this.locations.add("Rua Sara Afonso, n.º 105-117,4460-841 "
                + "Sra. da Hora, Portugal");
        this.locations.add("R. Abade Inácio Pimentel,4785-273 Trofa,Portugal");
        this.locations.add("R. Marquês de Pombal,4560-682 Penafiel,Portugal");
        this.locations.add("Largo da Estação,4700-223 Maximinos - "
                + "Braga,Portugal");
    }
    
    /**
     * Get all cartrackers.
     * 
     * @return List of cartrackers.
     */
    public List<String> getCartrackers() {
        return this.cartrackers;
    }

    /**
     * Setup reader.
     *
     * @param cartracker.
     * @throws FileNotFoundException .
     */
    public void setupStream(String cartracker) throws FileNotFoundException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(PathService.PROJECT_ROOT + "\\config_"
                                        + cartracker + ".txt")
                        )
                )
        );
    }

    /**
     * Get coordinates from google for directionInput.
     *
     * @param directionInput.
     * @return list of points.
     */
    public List<Point> getCoordinatesFromGoogle(DirectionInput directionInput) {
        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        DirectionsApiRequest request = DirectionsApi.getDirections(
                context,
                directionInput.getFrom(),
                directionInput.getTo());
        List<LatLng> latlongList = null;

        try {
            DirectionsResult routes = request.await();

            for (DirectionsRoute route : routes.routes) {
                latlongList = route.overviewPolyline.decodePath();
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        List<Point> points = new ArrayList<>();

        for (LatLng latLng : latlongList) {
            points.add(new Point(latLng.lat, latLng.lng));
        }

        return points;
    }

    /**
     * Get random directioninput for start- and endposition.
     *
     * @return directioninput.
     */
    public DirectionInput getRandomDirectioninput() {
        SecureRandom r = new SecureRandom();
        int i1 = r.nextInt(this.locations.size());
        int i2;

        do {
            i2 = r.nextInt(this.locations.size());
        } while (i1 == i2);

        DirectionInput input = new DirectionInput(
                this.locations.get(i1), this.locations.get(i2));

        return input;
    }

    /**
     * Generate files for roadusages.
     *
     * @param cartracker id for config file.
     * @param date date for generating roadusages.
     */
    public void generateFiles(String cartracker, Date date) {
        if (cartracker != null && !cartracker.isEmpty() && date != null &&
                this.cartrackers.contains(cartracker)) {        
            try {
                //Setup stream for configId.                
                this.setupStream(cartracker);

                //Read config file.
                String file = this.reader.readLine();
                String[] fileParam = file.split(",");
                String index = fileParam[1].substring(fileParam[1].indexOf("=") + 1);
                int fileIndex = Integer.parseInt(index);
                String ride = fileParam[2].substring(fileParam[2].indexOf("=") + 1);
                Long rideID = Long.parseLong(ride);

                //Get points from google.
                DirectionInput input = this.getRandomDirectioninput();
                List<Point> points = this.getCoordinatesFromGoogle(input);

                Point previous = null;

                for (Point p : points) {
                    //Get parameters.
                    Double xCoordinate = p.getLatitude();
                    Double yCoordinate = p.getLongitude();
                    Double meter = 0.0;
                    Boolean last = false;

                    //Chech if current position is last in list.
                    if (points.indexOf(p) == (points.size() - 1)) {
                        last = true;
                    }

                    //Calculate meters between this point and previous point.
                    if (previous != null) {
                        List<Point> ps = new ArrayList<>();
                        ps.add(previous);
                        ps.add(p);
                        meter = NavUtils.getTotalDistance(ps);
                    }

                    //Create json array.
                    Map<String, Object> position = new HashMap<>();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String momentString = df.format(date);
                    position.put("moment", momentString);
                    position.put("xCoordinate", xCoordinate);
                    position.put("yCoordinate", yCoordinate);
                    position.put("meter", meter);
                    position.put("rideId", rideID.toString());
                    position.put("last", last);

                    //Create file for point.
                    String fileName = cartracker + "-" + fileIndex + ".json";
                    FileWriter fileWriter = new FileWriter(PathService.PROJECT_ROOT
                            + "\\output\\" + fileName);
                    String output;

                    //Write file.
                    try (BufferedWriter writer
                            = new BufferedWriter(fileWriter)) {
                        Gson gson = new Gson();
                        output = gson.toJson(position);
                        writer.write(output);
                    }

                    //Send position through JMS.
                    this.sendPositionBean.sendPosition(output, cartracker,
                            Integer.toUnsignedLong(fileIndex));

                    previous = p;
                    fileIndex++;
                }

                //Update config file.
                rideID++;
                String output = "cartrackerID=" + cartracker + ",fileIndex="
                        + fileIndex + ",ride=" + rideID;
                FileWriter fileWritter = new FileWriter(PathService.PROJECT_ROOT
                        + "\\config_" + cartracker + ".txt", false);

                try (BufferedWriter writer2 = new BufferedWriter(fileWritter)) {
                    writer2.write(output);
                }
            } catch (IOException ex) {
                Logger.getLogger(PathService.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        } else {
            throw new IllegalArgumentException("Parameters not valid");
        }
    }

    /**
     * Generate GpsSimulatorInstance.
     *
     * @return GpsSimulatorInstance.
     */
    public GpsSimulatorInstance generate() {
        DirectionInput input = this.getRandomDirectioninput();

        Set<Long> instanceIds = new HashSet<>(this.taskFutures.keySet());
        List<Point> points = this.getCoordinatesFromGoogle(input);

        GpsSimulator gpsSimulator = new GpsSimulator();
        gpsSimulator.setShouldMove(true);
        gpsSimulator.setSpeedInKph(40d);
        gpsSimulator.setId(this.instanceCounter);

        instanceIds.add(this.instanceCounter);
        this.prepareGpsSimulator(gpsSimulator, points);

        Future<?> future = this.taskExecutor.submit(gpsSimulator);
        GpsSimulatorInstance instance = new GpsSimulatorInstance(
                this.instanceCounter, gpsSimulator, future);
        this.taskFutures.put(this.instanceCounter, instance);
        this.instanceCounter++;
        return instance;
    }

    /**
     * Prepare gps simulator.
     *
     * @param gpsSimulator.
     * @param points.
     * @return GpsSimulator.
     */
    public GpsSimulator prepareGpsSimulator(GpsSimulator gpsSimulator,
            List<Point> points) {
        gpsSimulator.setCurrentPosition(null);
        final List<Leg> legs = this.createLegsList(points);
        gpsSimulator.setLegs(legs);
        gpsSimulator.setStartPosition();
        return gpsSimulator;
    }

    /**
     * Creates list of legs in the path.
     *
     * @param points.
     */
    private List<Leg> createLegsList(List<Point> points) {
        final List<Leg> legs = new ArrayList<>();

        for (int i = 0; i < (points.size() - 1); i++) {
            Leg leg = new Leg();
            leg.setId(i);
            leg.setStartPosition(points.get(i));
            leg.setEndPosition(points.get(i + 1));
            Double length = NavUtils.getDistance(points.get(i),
                    points.get(i + 1));
            leg.setLength(length);
            Double heading = NavUtils.getBearing(points.get(i),
                    points.get(i + 1));
            leg.setHeading(heading);
            legs.add(leg);
        }

        return legs;
    }
}
