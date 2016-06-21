/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.jms;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import service.websockets.MonitoringServerSockets;

/**
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/LMS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveStatus'")
})
public class ReceiveFunctionalStatus implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveFunctionalStatus.class.getName()); 
    
    @Inject
    private MonitoringService service;
    
    @Inject
    private MonitoringServerSockets sockets;

    /**
     * receives message from system VS, RAD, ASS
     *
     * @param message contains systemName and testresult
     */
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;

            // Get message values
            String systemName = mapMessage.getString("system");
            String dateString = mapMessage.getString("date");
            String newDateString = mapMessage.getString("newDate");
    
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        
            LOGGER.log(Level.INFO, "systemName {0}",systemName);

            Date date = null;
            Date newDate = null;
            try {
                date = dateFormat.parse(dateString);
                newDate = dateFormat.parse(newDateString);
            } catch (ParseException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            this.service.processTestResults(systemName, date, newDate);
        } catch (JMSException ex) {
            LOGGER.log(Level.INFO, "Highlevel catch?");

            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        try {
            this.sockets.updateMonitoring();
        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, null, ex);
        } 
        
    }
}
