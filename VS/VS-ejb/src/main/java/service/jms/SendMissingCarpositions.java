/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
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
import javax.jms.MapMessage;

/**
 *
 * @author Linda
 */
@Stateless
public class SendMissingCarpositions {
    private static final Logger LOGGER = Logger
            .getLogger(SendMissingCarpositions.class.getName());
    
    @Inject
    @JMSConnectionFactory("jms/VSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/ASS/queue")
    private Destination queue;
    
    public void sendMissingNumbers(String cartrackerId, List<Long> serialnumbers) {
        try {
            
            // Create MapMessage
            MapMessage mapMessage = this.context.createMapMessage();
            System.out.println(cartrackerId);
            mapMessage.setStringProperty("cartrackerid", cartrackerId);
            Gson gson = new Gson();
            String jsonlist = gson.toJson(serialnumbers);
            mapMessage.setString("listNumbers", jsonlist);
            // Send message
            this.context.createProducer().send(this.queue, mapMessage);
            
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
