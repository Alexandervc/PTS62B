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
public class SendTestRequestVS {
    @Inject
    @JMSConnectionFactory("jms/VSConnectionFactory")
    private JMSContext contextVS;
    
    @Resource(lookup="jms/VS/queue")
    private Destination queueVS;
    
    /**
     * Send request for testresult from system VS
     */
    public void sendRequest() {
        try {
            
            MapMessage mapMessage = contextVS.createMapMessage();
            mapMessage.setStringProperty("method", "receiveTestRequest");
            mapMessage.setString("message", "Testresults Request");
            
            contextVS.createProducer().send(queueVS, mapMessage);
        } catch (JMSException ex) {
            Logger.getLogger(SendTestRequestVS.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
