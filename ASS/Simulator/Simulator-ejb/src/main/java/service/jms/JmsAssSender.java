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
 *
 * @author Alexander
 */
@Stateless
public class JmsAssSender {
    @Inject
    @JMSConnectionFactory("jms/VSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup = "jms/VS/queue")
    private Destination destination;

    public void sendPosition(String jsonPosition, Long cartrackerId, 
            Long serialNumber) {
        try {
            Logger.getLogger(JmsAssSender.class.getName())
                    .log(Level.INFO, jsonPosition);
            
            MapMessage mapMessage = this.context.createMapMessage();
            mapMessage.setStringProperty("method", "receiveCarpositions");
            mapMessage.setLong("cartrackerId", cartrackerId);
            mapMessage.setLong("serialNumber", serialNumber);
            mapMessage.setString("carposition", jsonPosition);
            
            this.context.createProducer().send(this.destination, mapMessage);
        } catch (JMSException ex) {
            Logger.getLogger(JmsAssSender.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
