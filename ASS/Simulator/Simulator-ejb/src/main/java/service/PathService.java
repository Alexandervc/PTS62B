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
import java.util.List;
import java.util.Map;
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
import model.Point;
import service.jms.SendPositionBean;
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
        this.cartrackers.add("PT121314151");
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
     * Generate files for roadusages.
     *
     * @param cartracker id for config file.
     */
    public void generateFiles(String cartracker) {
        if (cartracker != null && !cartracker.isEmpty() &&
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
                
                String pos = fileParam[3].substring(fileParam[3].indexOf("=") + 1);
                int startPositionIndex = Integer.parseInt(pos);

                //Get random direction input.
                SecureRandom r = new SecureRandom();
                int endPositionIndex;

                do {
                    endPositionIndex = r.nextInt(this.locations.size());
                } while (startPositionIndex == endPositionIndex);

                String startPosition = this.locations.get(startPositionIndex);
                String endPosition = this.locations.get(endPositionIndex);

                DirectionInput input = new DirectionInput(
                        startPosition, endPosition);
                
                //Get points from google.
                List<Point> points = this.getCoordinatesFromGoogle(input);

                Point previous = null;

                for (Point p : points) {
                    //Get parameters.
                    Date moment = new Date();
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
                    String momentString = df.format(moment);
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
                String output = "cartrackerID=" + cartracker 
                        + ",fileIndex=" + fileIndex 
                        + ",ride=" + rideID 
                        + ",position=" + endPositionIndex;
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
}
