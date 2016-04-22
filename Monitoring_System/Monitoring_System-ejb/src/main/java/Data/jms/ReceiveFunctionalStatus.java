/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.jms;

import common.domain.TestType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import service.MonitoringService;

/**
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveFunctionalStatus'")
})
public class ReceiveFunctionalStatus implements MessageListener {

    @Inject
    private MonitoringService service;

    /**
     * receives message from system VS, RAD, ASS
     *
     * @param message contains systemName and testresult
     */
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;

            // Get system Name
            String systemName = mapMessage.getString("system");

            // Get result boolean
            Boolean testresult = mapMessage.getBoolean("result");

            // TODO save in db
            this.service.saveTestresult(systemName, testresult, TestType.FUNCTIONAL);

        } catch (JMSException ex) {
            Logger.getLogger(ReceiveFunctionalStatus.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
