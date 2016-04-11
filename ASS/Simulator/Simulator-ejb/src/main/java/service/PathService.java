package service;

import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import model.Point;
import model.DirectionInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import model.GpsSimulatorInstance;
import model.Leg;
import service.jms.JmsAssSender;
import simulator.GpsSimulator;
import support.NavUtils;

/**
 *
 * @author Melanie
 */
@Stateless
public class PathService implements IPathService, Serializable {    
    private String PROJECT_ROOT = "C:\\Users\\Alexander\\Documents\\GitHub\\PTS62B\\ASS\\Simulator";
    
    private String APIkey = "AIzaSyCDUV1tIzDx5or4V-wrAsSN9lc8Gvpsz6Y";
    private BufferedReader reader;
    private BufferedWriter writer;
    
    private List<String> locations;
    private Map<Long, GpsSimulatorInstance> taskFutures = new HashMap<>();
    private ExecutorService taskExecutor = Executors.newSingleThreadExecutor();    
    long instanceCounter = 1;
    
    @Inject
    private JmsAssSender assSender;
    
    @PostConstruct
    public void setupLocations() {
        locations = new ArrayList<>();
        locations.add("R. do Ouro,1150-060 Lisboa,Portugal");
        locations.add("R. Alm. Gago Coutinho,Albufeira,Portugal");
        locations.add("R. Dr. Faria e Silva 34,Portugal");
        locations.add("Entrada da Barca, 7630-340 Zambujeira do Mar,7630-734,"
                + "Portugal");
        locations.add("R. de Serpa Pinto,84,7940-172 Cuba,Portugal");
        locations.add("Terreiro da Sé,4050-573 Porto,Portugal");
        locations.add("Rua Sara Afonso, n.º 105-117,4460-841 Sra. da Hora,"
                + "Portugal");
        locations.add("R. Abade Inácio Pimentel,4785-273 Trofa,Portugal");
        locations.add("R. Marquês de Pombal,4560-682 Penafiel,Portugal");
        locations.add("Largo da Estação,4700-223 Maximinos - Braga,Portugal");
    }
    
    public void setupStream() throws FileNotFoundException {
        reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(PROJECT_ROOT + "\\config.txt")
                        )
                )
        );
    }

    @Override
    public List<Point> getCoordinatesFromGoogle(DirectionInput directionInput) {
        GeoApiContext context = new GeoApiContext().setApiKey(this.APIkey);
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
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        List<Point> points = new ArrayList<>();

        for (LatLng latLng : latlongList) {
            points.add(new Point(latLng.lat, latLng.lng));
        }

        return points;
    }
    
    @Override
    public DirectionInput getRandomDirectioninput() {
        Random r = new Random();
        int i1 = r.nextInt(locations.size());
        int i2 = 0;
        
        do {
            i2 = r.nextInt(locations.size());
        } while (i1 == i2);
        
        DirectionInput input = new DirectionInput(
                locations.get(i1), locations.get(i2));     
        
        return input;
    }
    
    @Override
    public void generateFile() {
        try {
            setupStream();
            
            String file = reader.readLine();
            String[] fileParam = file.split(",");
            String cartrackerID = fileParam[0].substring(fileParam[0].
                    indexOf("=")+1);
            String index = fileParam[1].substring(fileParam[1].indexOf("=")+1);
            int fileIndex = Integer.parseInt(index);            
            
            DirectionInput input = getRandomDirectioninput();
            List<Point> points = getCoordinatesFromGoogle(input);
            
            double totalDistance = NavUtils.getTotalDistance(points);
            
            Point previous = null;
            
            for (Point p : points) {
                String fileName = cartrackerID + "-" + fileIndex + ".json";
                
                FileWriter fileWritter = new FileWriter(PROJECT_ROOT + "\\output\\" + fileName);
                BufferedWriter writer = new BufferedWriter(fileWritter);               
                
                Date moment = new Date();
                Double xCoordinate = p.getLatitude();
                Double yCoordinate = p.getLongitude();
                Double meter = 0.0;
                
                if (previous != null) {
                    List<Point> ps = new ArrayList<>();
                    ps.add(previous);
                    ps.add(p);
                    meter = NavUtils.getTotalDistance(ps);
                }
                
                //Create json array
                Map<String, Object> position = new HashMap<>();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String momentString = df.format(moment);
                position.put("moment", momentString);
                position.put("xCoordinate", xCoordinate);
                position.put("yCoordinate", yCoordinate);
                position.put("meter", meter);
                
                //Write file
                Gson gson = new Gson();
                String output = gson.toJson(position);
                writer.write(output);
                writer.close();
                
                //Send JMS
                assSender.sendPosition(output, Long.valueOf(cartrackerID),
                        Integer.toUnsignedLong(fileIndex));
                
                previous = p;
                fileIndex++;
            }
            
            String output = "cartrackerID=" + cartrackerID + ",fileIndex=" + fileIndex;
            FileWriter fileWritter = new FileWriter(PROJECT_ROOT + "\\config.txt", false);
            BufferedWriter writer2 = new BufferedWriter(fileWritter);
            writer2.write(output);
            writer2.close();
                    
        } catch (IOException ex) {
            Logger.getLogger(PathService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public GpsSimulatorInstance generate() {
        DirectionInput input = getRandomDirectioninput();
                
        Point lookAtPoint = null;
        Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());
        List<Point> points = getCoordinatesFromGoogle(input);

        if (lookAtPoint == null) {
            lookAtPoint = points.get(0);
        }

        GpsSimulator gpsSimulator = new GpsSimulator();
        //gpsSimulator.setMessageChannel(messageChannel);
        gpsSimulator.setShouldMove(true);
        gpsSimulator.setExportPositionsToKml(true);
        gpsSimulator.setSpeedInKph(40d);
        gpsSimulator.setId(instanceCounter);

        instanceIds.add(instanceCounter);
        prepareGpsSimulator(gpsSimulator, points);

        Future<?> future = taskExecutor.submit(gpsSimulator);
        GpsSimulatorInstance instance = new GpsSimulatorInstance(
                instanceCounter, gpsSimulator, future);
        taskFutures.put(instanceCounter, instance);
        instanceCounter++;
        return instance;
    }

    @Override
    public GpsSimulator prepareGpsSimulator(GpsSimulator gpsSimulator, 
            List<Point> points) {
        gpsSimulator.setCurrentPosition(null);
        final List<Leg> legs = createLegsList(points);
        gpsSimulator.setLegs(legs);
        gpsSimulator.setStartPosition();
        return gpsSimulator;
    }

    /**
     * Creates list of legs in the path
     *
     * @param points
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
