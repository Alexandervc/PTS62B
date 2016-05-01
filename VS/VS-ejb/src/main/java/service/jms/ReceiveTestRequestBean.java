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
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/LMS/monitoringTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='getStatus'")//,
    /*@ActivationConfigProperty(propertyName = "addressList",
            propertyValue = "192.168.24.70:7676")*/
})
public class ReceiveTestRequestBean implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveTestRequestBean.class.getName()); 
    
    @Inject
    private SendTestResultsBean sender;

    /**
     * receives request for testresult from LMS
     * start JUnit test inside this system
     * @param message contains request
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

        sender.sendTestResults(date);
    }
}
