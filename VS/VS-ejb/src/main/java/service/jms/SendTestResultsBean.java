/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import java.text.DateFormat;
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
     * @param date The date of the test.
     */
    public void sendTestResults(String date) {
        try {         
            MapMessage mapMessage = context.createMapMessage();
            // send result to method receiveTestresults
            mapMessage.setStringProperty("method", "receiveStatus");

            // set message string systemName
            mapMessage.setString("system", "VS");
            // set message boolean result
            //mapMessage.setBoolean("result", result.wasSuccessful());
            
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            String dateString = dateFormat.format(new Date());
            

            mapMessage.setString("date", date);
            mapMessage.setString("newDate", dateString);
            JMSProducer producer = context.createProducer();
            producer.setTimeToLive(60000);
            producer.send(this.queue, mapMessage);
            this.context.createProducer().send(this.queue, mapMessage);
            System.out.println("message sent from:" +
            mapMessage.getString("system") +
            mapMessage.getString("date"));
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
