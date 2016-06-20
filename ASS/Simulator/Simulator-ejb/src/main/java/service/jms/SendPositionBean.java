package service.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * SendPositionBean Class.
 * @author Alexander
 */
@Stateless
public class SendPositionBean {
    @Inject
    @JMSConnectionFactory("jms/VSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup = "jms/VS/queue")
    private Destination destination;
    
    private static final Logger LOGGER = Logger
            .getLogger(SendPositionBean.class.getName());

    /**
     * Send the position to VS.
     * @param jsonPosition JSON string of the position to send.
     * @param cartrackerId The cartrackerId to send the position for.
     * @param serialNumber A number to indicate the order of the send positions.
     */
    public void sendPosition(String jsonPosition, String cartrackerId, 
            Long serialNumber) {
        try {
            // Make mapMessage
            MapMessage mapMessage = this.context.createMapMessage();
            mapMessage.setStringProperty("method", "receiveCarpositions");
            mapMessage.setString("cartrackerId", cartrackerId);
            mapMessage.setLong("serialNumber", serialNumber);
            mapMessage.setString("carposition", jsonPosition);
            
            // Send mapMessage
            this.context.createProducer().send(this.destination, mapMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Send the position to VS.
     * @param cartrackerId The cartrackerId to send the position for.
     * @param jsonPositions List of JSON strings of the position to send.
     * @param rideId Id for ride.
     */
    public void sendPositions(String cartrackerId, Integer rideId, 
            String jsonPositions) {
        try {            
            // Make mapMessage
            MapMessage mapMessage = this.context.createMapMessage();
            mapMessage.setStringProperty("method", "receiveCarpositions");
            mapMessage.setString("cartrackerId", cartrackerId);
            mapMessage.setInt("rideId", rideId);
            mapMessage.setString("carpositions", jsonPositions);
            
            // Send mapMessage
            this.context.createProducer().send(this.destination, mapMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
