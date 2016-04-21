/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
public class SendTestRequest {
    @Inject
    @JMSConnectionFactory("jms/VSConnectionFactory")
    private JMSContext contextVS;
    
    @Resource(lookup="jms/VS/queue")
    private Destination queueVS;
    
    public void sendRequestVS() {
        try {
            
            MapMessage mapMessage = contextVS.createMapMessage();
            mapMessage.setStringProperty("method", "receiveTestRequest");
            mapMessage.setString("message", "TestresultsRequest");
            
            contextVS.createProducer().send(queueVS, mapMessage);
        } catch (JMSException ex) {
            Logger.getLogger(SendTestRequest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
