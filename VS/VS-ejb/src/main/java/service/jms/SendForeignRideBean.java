/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import domain.CarPosition;
import dto.ForeignMessage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * The bean that sends foreign rides to the central queue.
 * @author Alexander
 */
@Stateless
public class SendForeignRideBean {
    private static final Logger LOGGER = Logger
            .getLogger(SendForeignRideBean.class.getName());
    
    @Inject
    @JMSConnectionFactory("jms/CSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/CS/queue")
    private Destination queue;
    
    /**
     * Send the given information via JMS to the central queue.
     * @param cartrackerId The foreign cartracker.
     * @param totalPrice The total price of the ride.
     * @param carpositions The carPositions of the ride.
     * @param countryCodeTo The code of the country where the foreign cartracker
     *      is from.
     * @param countryCodeFrom The code of our own country.
     */
    public void sendForeignRide(String cartrackerId, Double totalPrice,
            List<CarPosition> carpositions, String countryCodeTo, 
            String countryCodeFrom) {
        try {
            // Convert to foreignMessage
            ForeignMessage foreignMessage = new ForeignMessage(cartrackerId, 
                    totalPrice, carpositions);
            
            // To JSON
            Gson gson = new Gson();
            String jsonForeignMessage = gson.toJson(foreignMessage);
            
            // Create Textmessage
            TextMessage textMessage = this.context
                    .createTextMessage(jsonForeignMessage);
            textMessage.setStringProperty("countryCodeTo", countryCodeTo);
            textMessage.setStringProperty("countryCodeFrom", countryCodeFrom);
            
            // Send message
            this.context.createProducer().send(this.queue, textMessage);
            
            LOGGER.log(
                    Level.INFO, 
                    "[Sent] " + cartrackerId
                            + " - " + foreignMessage.getTotalPrice());
            
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
