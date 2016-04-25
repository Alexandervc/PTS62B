/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import org.junit.runner.Result;

/**
 *
 * @author Linda
 */
@Stateless
public class SendTestResultsBean {

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
//        try {
//            
//            MapMessage mapMessage = context.createMapMessage();
//            // send result to method receiveTestresults
//            mapMessage.setStringProperty("method", "receiveFunctionalStatus");
//
//            // set message string systemName
//            mapMessage.setString("system", "VS");
//            // set message boolean result
//            mapMessage.setBoolean("result", result.wasSuccessful());
//
//            // send message to LMS
//            this.context.createProducer().send(this.queue, mapMessage);
//        } catch (JMSException ex) {
//            Logger.getLogger(SendTestResultsBean.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
    }

}
