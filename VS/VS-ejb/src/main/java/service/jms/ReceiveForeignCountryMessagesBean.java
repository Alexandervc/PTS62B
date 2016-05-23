/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.CarPositionManager;
import com.google.gson.Gson;
import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import dto.ForeignMessage;
import dto.ForeignPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
@MessageDriven(mappedName = "jms/CS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "countryCode='PT'")
})
public class ReceiveForeignCountryMessagesBean implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveForeignCountryMessagesBean.class.getName()); 
    
    @Inject
    private CarPositionManager carPositionManager;
    
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            
            Gson gson = new Gson();
            ForeignMessage foreignMessage = gson.fromJson(textMessage.getText(),
                    ForeignMessage.class);
            
            List<ForeignPosition> foreignPositions = 
                    foreignMessage.getPositions();
            List<CarPosition> carPositions = new ArrayList<>();
            
            // Sort by date, this ensures that the list is in the right order.
            // The order is used to calculate the distance between two 
            // positions.
            Collections.sort(foreignPositions);
            
            String foreignCountryRideId = "PT" + this.carPositionManager
                    .getNextRideIdOfCountryCode("PT");
            
            for (int i = 0; i < foreignPositions.size(); i++) {
                ForeignPosition currentPosition = foreignPositions.get(i);
                ForeignPosition previousPosition = null;
                
                if (i > 0) { 
                    previousPosition = foreignPositions.get(i - 1);
                }
                                
                // Get the cartracker from the foreignMessage.                
                Cartracker carTracker = this.carPositionManager
                        .findCartracker(foreignMessage.getCartrackerId());
                
                // Create the foreign country road.
                Road road = new Road(
                        "Foreign Country Road", 
                        RoadType.FOREIGN_COUNTRY_ROAD);
                
                // Calculate the distance in meters between the current and 
                // previous positions.
                Double distanceToPrevious = 0.0;
                        
                if (previousPosition != null) {
                    distanceToPrevious = Math.hypot(
                            previousPosition.getX() - currentPosition.getX(),
                            previousPosition.getY() - currentPosition.getY());
                }
                
                //Check if position is last of ride.
                // True if last element of carPositions.
                Boolean lastOfRide = false;
                
                if (i >= foreignPositions.size() - 1) {
                    lastOfRide = true;
                }
                
                // Map the foreign position to the carPosition.
                CarPosition carPosition = new CarPosition(
                        carTracker,
                        new Date(currentPosition.getDatetime()),
                        currentPosition.getX(),
                        currentPosition.getY(),
                        road,
                        distanceToPrevious,
                        foreignCountryRideId,                        
                        lastOfRide); 
                
                carPositions.add(carPosition);
            }
            
            // Store the CarPositions in VS and the ForeignCountryRide in RAD.
            this.carPositionManager
                    .processForeignCarRide(
                            carPositions, 
                            foreignCountryRideId, 
                            foreignMessage.getTotalPrice());
            
        } catch (JMSException ex) {
            // Parsing the message was not successfull.
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
}
