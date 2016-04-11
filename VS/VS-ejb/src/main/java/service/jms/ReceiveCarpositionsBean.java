/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.RoadUsageManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.CarPosition;
import domain.Cartracker;
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
 *
 * @author Alexander
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveCarpositions'")
})
public class ReceiveCarpositionsBean implements MessageListener {

    @Inject
    private CarPositionService carPositionService;

    @Override
    public void onMessage(Message message) {
        try {
            Logger.getLogger(ReceiveCarpositionsBean.class.getName())
                    .log(Level.INFO, "receiveCarPositions");

            MapMessage mapMessage = (MapMessage) message;
            Logger.getLogger(ReceiveCarpositionsBean.class.getName())
                    .log(Level.INFO, mapMessage.toString());

            // Get values
            Long cartrackerId = mapMessage.getLong("cartrackerId");
            // TODO check
            Long serialNumber = mapMessage.getLong("serialNumber");

            // Get position
            String jsonPostion = mapMessage.getString("carposition");
            Gson gson = new Gson();
            // TODO?
            //CarPosition cp = gson.fromJson(jsonPostion, CarPosition.class);
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> position = gson.fromJson(jsonPostion, type);

            String momentString = (String) position.get("moment");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date moment = df.parse(momentString);
            Double xCoordinate = (Double) position.get("xCoordinate");
            Double yCoordinate = (Double) position.get("yCoordinate");
            Double meter = (Double) position.get("meter");

            // TODO road op andere manier??
            String roadName = "test";

            this.carPositionService.saveCarPosition(cartrackerId, moment,
                    xCoordinate, yCoordinate, roadName, meter);
        } catch (JMSException ex) {
            Logger.getLogger(ReceiveCarpositionsBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ReceiveCarpositionsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
