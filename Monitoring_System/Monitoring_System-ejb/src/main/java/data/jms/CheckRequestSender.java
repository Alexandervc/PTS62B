/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.jms;

import java.text.SimpleDateFormat;
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
public class CheckRequestSender {
    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/LMS/monitoringTopic")
    private Destination topic;
    
      
    public void requestChecks(){
        try {
            MapMessage message = context.createMapMessage();
            message.setStringProperty("method", "getStatus");
            java.util.Date date = new java.util.Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentTime = df.format(date);
            message.setString("time", currentTime);

            context.createProducer().send(topic, message);
        } catch (JMSException ex) {
            Logger.getLogger(CheckRequestSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
