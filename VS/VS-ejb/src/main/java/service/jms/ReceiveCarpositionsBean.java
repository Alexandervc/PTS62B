/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import service.CarPositionService;

/**
 * The messagedriven bean for receiving carpositions from ASS.
 * @author Alexander
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveCarpositions'")
})
public class ReceiveCarpositionsBean implements MessageListener {
    private static final Logger LOGGER = Logger
            .getLogger(ReceiveCarpositionsBean.class.getName());
    
    @Inject
    private CarPositionService carPositionService;

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;

            // Get values
            String cartrackerId = mapMessage.getString("cartrackerId");
            // TODO check
            Long serialNumber = mapMessage.getLong("serialNumber");

            // Get position
            String jsonPostion = mapMessage.getString("carposition");
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> position = gson.fromJson(jsonPostion, type);

            String momentString = (String) position.get("moment");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date moment = df.parse(momentString);
            Double xCoordinate = (Double) position.get("xCoordinate");
            Double yCoordinate = (Double) position.get("yCoordinate");
            Double meter = (Double) position.get("meter");
            String rideId = (String) position.get("rideId");
            Boolean lastOfRide = (Boolean) position.get("last");

            // TODO road op andere manier??
            String roadName = "test";

            this.carPositionService.processCarPosition(cartrackerId, moment,
                    xCoordinate, yCoordinate, roadName, meter, 
                    Integer.parseInt(rideId), null, lastOfRide);
        } catch (JMSException | ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
