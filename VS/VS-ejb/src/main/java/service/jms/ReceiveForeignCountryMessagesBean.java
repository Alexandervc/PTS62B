/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.CarPositionManager;
import business.RoadManager;
import com.google.gson.Gson;
import domain.CarPosition;
import domain.Cartracker;
import domain.Coordinate;
import domain.Road;
import domain.RoadType;
import dto.ForeignMessage;
import dto.ForeignPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Provides functionality regarding receiving messages from the Central System.
 * @author Jesse
 */
@MessageDriven(mappedName = "jms/CS/filteredQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "countryCodeTo='PT'")
// TODO DEPLOY: UNCOMMENT
//    ,@ActivationConfigProperty(propertyName = "addressList", propertyValue = "192.168.24.68:7676")
})
public class ReceiveForeignCountryMessagesBean implements MessageListener {
    
    private static final Logger LOGGER = Logger
            .getLogger(ReceiveForeignCountryMessagesBean.class.getName());
    
    private static final Map<String, String> COUNTRY_NAMES = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<>();
        result.put("PT", "Portugal");
        result.put("BE", "Belgium");
        result.put("NL", "The Netherlands");
        result.put("LU", "Luxembourg");
        return Collections.unmodifiableMap(result);
    }

    @Inject
    private RoadManager roadManager;
    
    @Inject 
    private CarPositionManager carPositionManager;
        
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;

            Gson gson = new Gson();
            ForeignMessage foreignMessage = gson.fromJson(textMessage.getText(),
                                                          ForeignMessage.class);
            
            LOGGER.log(Level.INFO, 
                       "[Message] " + foreignMessage.getCartrackerId() 
                       + " - " + foreignMessage.getTotalPrice());

            List<ForeignPosition> foreignPositions = 
                    foreignMessage.getPositions();
            List<CarPosition> carPositions = new ArrayList<>();

            // Sort by date, this ensures that the list is in the right order.
            Collections.sort(foreignPositions);
            
            Long foreignCountryRideId = this.carPositionManager
                    .getNextRideIdOfCountryCode();
            
            LOGGER.log(Level.INFO, 
                       "[Generated] " + foreignMessage.getCartrackerId() 
                       + " - " + foreignCountryRideId);

            for (int i = 0; i < foreignPositions.size(); i++) {
                ForeignPosition currentPosition = foreignPositions.get(i);

                String roadName = COUNTRY_NAMES.get(
                        textMessage.getStringProperty("countryCodeFrom"));
                
                LOGGER.log(Level.INFO, 
                           "[RoadName] " + foreignMessage.getCartrackerId() 
                           + " - " + roadName);
                
                Road road = this.findOrReplaceRoadByName(roadName);
                Date date = this.parseDate(currentPosition.getDatetime());
                
                // Skip the message if date could not be parsed.
                if (date == null) {
                    LOGGER.log(Level.INFO, 
                               "[ERROR] " + foreignMessage.getCartrackerId() 
                               + " - could not parse: " 
                               + currentPosition.getDatetime());
                    
                    return;
                }

                // Get the cartracker from the foreignMessage.                
                Cartracker carTracker = this.carPositionManager
                        .findCartracker(foreignMessage.getCartrackerId());
                
                LOGGER.log(Level.INFO, 
                           "[Received] " + foreignMessage.getCartrackerId() 
                           + " - received cartracker");
                
                // True if last element of carPositions.
                Boolean isLastCarPosition = i >= foreignPositions.size() - 1;
                
                // Map the foreign position to the carPosition.
                CarPosition carPosition = new CarPosition(
                        carTracker,
                        date,
                        new Coordinate(currentPosition.getX(), 
                                       currentPosition.getY()),
                        road,
                        0D,
                        null,
                        foreignCountryRideId,
                        isLastCarPosition,
                        0L); 

                carPositions.add(carPosition);
            }

            // Store the CarPositions in VS and the ForeignCountryRide in RAD.
            this.carPositionManager
                    .processForeignCarRide(
                            carPositions, 
                            foreignCountryRideId, 
                            foreignMessage.getTotalPrice());
            
            LOGGER.log(Level.INFO, 
                       "[CarPosition] " + foreignMessage.getCartrackerId() 
                       + " - CarPositions stored for: " + foreignCountryRideId);

        } catch (JMSException ex) {
            // Parsing the message was not successfull.
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    private Road findOrReplaceRoadByName(String name) {
        Road road = this.roadManager.findRoadByName(name);
                
        // Create the foreign country road.
        if (road == null) {
            road = new Road(
                name, 
                RoadType.FOREIGN_COUNTRY_ROAD);

            this.roadManager.save(road);
        }
        
        return road;
    }
    
    private Date parseDate(String dateToParse) {
        Date date = null;
        
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    .parse(dateToParse);
        } catch (ParseException ex) {
            // If the date does not follow the date conventions, return null and,
            // skip the message.
            LOGGER.log(
                    Level.SEVERE, 
                    dateToParse + " -  " + ex);
        }
        
        return date;
    }
}
