/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.jms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;

/**
 *
 * @author Edwin.
 */
@Stateless
public class CheckRequestSender {
    
    private static final Logger LOGGER = Logger
            .getLogger(CheckRequestSender.class.getName()); 
    
    //The time to live for the messages that are being send in this class.
    private static final int TIMEOUTTIME = 60000;
    
    //Connection factory for the connection with topics/queues on LMS.
    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;
    
    //The topic where all the test requests from monitoring will be send to.
    @Resource(lookup="jms/LMS/monitoringTopic")
    private Destination topic;
    
      
    /**
     * Sends a message in the monitoring topic to request the systems that are
     * currently listening to the topic to validate their system and send the 
     * state back.
     * @param date The date the tests were requested.
     */
    public void requestChecks(Date date){
        try {
            MapMessage message = this.context.createMapMessage();
            message.setStringProperty("method", "getStatus");
            JMSProducer producer = this.context.createProducer();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentTime = df.format(date);
            message.setString("date", currentTime);
            producer.setTimeToLive(TIMEOUTTIME);
            producer.send(this.topic, message);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
