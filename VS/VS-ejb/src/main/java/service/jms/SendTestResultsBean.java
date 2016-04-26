/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.jms.JMSProducer;
import javax.jms.MapMessage;

/**
 *
 * @author Linda
 */
@Stateless
public class SendTestResultsBean {
    
    private static final Logger LOGGER = Logger
            .getLogger(GenerateRoadUsagesBean.class.getName());

    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "jms/LMS/queue")
    private Destination queue;

    /**
     * send result to LMS
     * param Result result
     */
    public void sendTestResults() {
        try {         
            MapMessage mapMessage = context.createMapMessage();
            // send result to method receiveTestresults
            mapMessage.setStringProperty("method", "receiveStatus");

            // set message string systemName
            mapMessage.setString("system", "VS");
            // set message boolean result
            //mapMessage.setBoolean("result", result.wasSuccessful());

            JMSProducer producer = context.createProducer();
            producer.setTimeToLive(60000);
            producer.send(this.queue, mapMessage);
            this.context.createProducer().send(this.queue, mapMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
