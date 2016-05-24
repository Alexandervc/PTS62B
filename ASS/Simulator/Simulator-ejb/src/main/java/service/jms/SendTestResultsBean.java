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
 *  The bean that sends the results of a test.
 * @author Edwin.
 */
@Stateless
public class SendTestResultsBean {
    
    private static final Logger LOGGER = Logger
            .getLogger(SendTestResultsBean.class.getName());
    
    // The timeout time for messages send from this class.
    private static final long TIMEOUT = 60000;

    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;

    /**
     * send result to LMS
     * param Result result
     * @param date The date of the test.
     * @param queue Destination to reply to.
     */
    public void sendTestResults(String date, Destination queue) {
        try {         
            MapMessage mapMessage = this.context.createMapMessage();
            // send result to method receiveTestresults
            mapMessage.setStringProperty("method", "receiveStatus");

            // set message string systemName
            mapMessage.setString("system", "ASS");
            // set message boolean result
            
            // Formats the date so that it can be send in a clear format.
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            String dateString = dateFormat.format(new Date());            

            mapMessage.setString("date", date);
            mapMessage.setString("newDate", dateString);
            JMSProducer producer = this.context.createProducer();
            producer.setTimeToLive(TIMEOUT);
            producer.send(queue, mapMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
