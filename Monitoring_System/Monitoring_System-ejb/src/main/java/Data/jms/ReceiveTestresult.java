/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.jms;

import common.domain.TestType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
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
            propertyValue = "method='receiveTestresults'")
})
public class ReceiveTestresult implements MessageListener {

    @Inject
    private MonitoringService service;

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
            Logger.getLogger(ReceiveTestresult.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
