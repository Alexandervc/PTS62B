/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import domain.CarPosition;
import dto.ForeignMessage;
import dto.RoadUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 *
 * @author Alexander
 */
@Stateless
public class SendForeignRideBean {
    @Inject
    @JMSConnectionFactory("jms/CSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/CS/queue")
    private Destination queue;
    
    private static final Logger LOGGER = Logger
            .getLogger(SendForeignRideBean.class.getName());
    
    /**
     * Send the given information via JMS to the central queue.
     * @param cartrackerId The foreign cartracker.
     * @param totalPrice The total price of the ride.
     * @param carpositions The carPositions of the ride.
     * @param countryCode The code of the country where the foreign cartracker
     *      is from.
     */
    public void sendForeignRide(String cartrackerId, Double totalPrice,
            List<CarPosition> carpositions, String countryCode) {
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
            textMessage.setStringProperty("countryCode", countryCode);
            
            // Send message
            this.context.createProducer().send(this.queue, textMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
