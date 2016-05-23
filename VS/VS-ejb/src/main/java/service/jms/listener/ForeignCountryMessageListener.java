/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms.listener;

import business.CarPositionManager;
import com.google.gson.Gson;
import domain.CarPosition;
import domain.Cartracker;
import domain.Road;
import domain.RoadType;
import dto.ForeignMessage;
import dto.ForeignPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import service.jms.ReceiveForeignCountryMessagesBean;

/**
 *
 * @author Jesse
 */
public class ForeignCountryMessageListener implements MessageListener {
    
    private static final Logger LOGGER = Logger
            .getLogger(ForeignCountryMessageListener.class.getName());
    
    @Inject
    private CarPositionManager carPositionManager;
    
    @Override
    public void onMessage(Message message) {
        try {
            LOGGER.log(Level.INFO, "Received foreign country message.");
            TextMessage textMessage = (TextMessage) message;

            Gson gson = new Gson();
            ForeignMessage foreignMessage = gson.fromJson(textMessage.getText(),
                    ForeignMessage.class);

            LOGGER.log(
                    Level.INFO, 
                    "Foreign country message parsed to ForeignMessage.");

            List<ForeignPosition> foreignPositions = 
                    foreignMessage.getPositions();
            List<CarPosition> carPositions = new ArrayList<>();

            // Sort by date, this ensures that the list is in the right order.
            // The order is used to calculate the distance between two 
            // positions.
            Collections.sort(foreignPositions);

            LOGGER.log(Level.INFO, "ForeignPositions sorted.");

            String foreignCountryRideId = "PT" + this.carPositionManager
                    .getNextRideIdOfCountryCode("PT");

            LOGGER.log(
                    Level.INFO,
                    "Created foreignCountryRideId: " + foreignCountryRideId);

            for (int i = 0; i < foreignPositions.size(); i++) {
                ForeignPosition currentPosition = foreignPositions.get(i);
                ForeignPosition previousPosition = i > 0 ? 
                        foreignPositions.get(i - 1) 
                        : null;

                // Get the cartracker from the foreignMessage.                
                Cartracker carTracker = this.carPositionManager
                        .findCartracker(foreignMessage.getCartrackerId());

                // Create the foreign country road.
                Road road = new Road(
                        "Foreign Country Road", 
                        RoadType.FOREIGN_COUNTRY_ROAD);

                // Calculate the distance in meters between the current and 
                // previous positions.
                Double distanceToPrevious = (previousPosition != null) ?
                        Math.hypot(
                            previousPosition.getX() - currentPosition.getX(),
                            previousPosition.getY() - currentPosition.getY())
                        : 0;

                Date date = new Date();

                try {
                    date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                            .parse(currentPosition.getDatetime());
                } catch (ParseException ex) {
                    LOGGER.log(
                            Level.SEVERE, 
                            currentPosition.getDatetime() + " - " + ex);

                    // If the date does not follow the date conventions, skip 
                    // the message.
                    return;
                }

                // Map the foreign position to the carPosition.
                CarPosition carPosition = new CarPosition(
                        carTracker,
                        date,
                        currentPosition.getX(),
                        currentPosition.getY(),
                        road,
                        distanceToPrevious,
                        foreignCountryRideId,
                        // True if last element of carPositions.
                        (i >= foreignPositions.size() - 1)); 

                carPositions.add(carPosition);
            }

            LOGGER.log(
                    Level.INFO, 
                    "Created " + carPositions.size() + " CarPositions");

            // Store the CarPositions in VS and the ForeignCountryRide in RAD.
            this.carPositionManager
                    .processForeignCarRide(
                            carPositions, 
                            foreignCountryRideId, 
                            foreignMessage.getTotalPrice());

            LOGGER.log(Level.INFO, foreignCountryRideId + " processed.");

        } catch (JMSException ex) {
            // Parsing the message was not successfull.
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }    
}
