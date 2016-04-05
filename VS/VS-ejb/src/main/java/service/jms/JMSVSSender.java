/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.RoadUsage;
import java.util.List;
import javax.annotation.Resource;
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
public class JMSVSSender {
    @Inject
    @JMSConnectionFactory("jms/__defaultConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/RAD/queue")
    private Destination queue;
    
    public void sendRoadUsages(List<RoadUsage> roadUsages) throws JMSException {
        // To JSON
        
        TextMessage textMessage = context.createTextMessage("TODO");
        textMessage.setStringProperty("method", "generateBill");
        
        context.createProducer().send(queue, textMessage);
    }
}
