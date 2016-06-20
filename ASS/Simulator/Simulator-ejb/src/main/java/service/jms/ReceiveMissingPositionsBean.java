/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
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
import service.SearchMissingPosition;

/**
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/ASS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveMissingPositions'")
})
public class ReceiveMissingPositionsBean implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveMissingPositionsBean.class.getName());

    @Inject
    private SearchMissingPosition search;

    @Override
    public void onMessage(Message message) {
        try{
            MapMessage mapMessage = (MapMessage) message;

            // Get values
            String cartrackerId = mapMessage.getString("CartrackerId");
            String jsonPositions = mapMessage.getString("listNumbers");
            
            Gson gson = new Gson();
            Type type = new TypeToken<List<Long>>() {
            }.getType();
            List<Long> positions
                    = gson.fromJson(jsonPositions, type);
            
            this.search.searchPositions(cartrackerId, positions);
            
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
