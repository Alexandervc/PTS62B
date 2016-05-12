/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * The messagebean that receives the messages concerning requests from the 
 * monitoring system.
 * @author Edwin.
 */
@MessageDriven(mappedName = "jms/LMS/monitoringTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='getStatus'")//,
        // TODO DEPLOY: UNCOMMENT
    //@ActivationConfigProperty(propertyName = "addressList",
    //       propertyValue = "192.168.24.70:7676")
})
public class ReceiveTestRequestBean implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveTestRequestBean.class.getName()); 
    
    @Inject
    private SendTestResultsBean sender;

    /**
     * Receives request for testresult from LMS
     * returns a message to indicate that it has been received.
     * @param message contains request.
     */
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        String date = null;
        try {
            date = mapMessage.getString("date");
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        // Gives the date from the original message so that it can be 
        // send back.
        this.sender.sendTestResults(date);
    }
}
