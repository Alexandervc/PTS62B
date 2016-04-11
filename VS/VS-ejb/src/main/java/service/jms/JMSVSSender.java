/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.RoadUsage;
import com.google.gson.Gson;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 *
 * @author Alexander
 */
@Stateless
public class JMSVSSender {
    @Inject
    @JMSConnectionFactory("jms/RADConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/RAD/queue")
    private Destination queue;
    
    public void sendRoadUsages(List<RoadUsage> roadUsages) throws JMSException {
        // To JSON
        //Gson gson = new Gson();
        //String jsonString = gson.toJson(roadUsages);
        String jsonString = "[";
        for(RoadUsage ru : roadUsages) {
            jsonString += "{\"roadName\":\"" + ru.getRoadName() + "\", \"roadType\":\"" + ru.getRoadType() + "\", \"km\":\"" + ru.getKm().toString() + "\"},";
        }
        jsonString = jsonString.substring(0, jsonString.length() -1) + "]";
        Logger.getLogger(JMSVSSender.class.getName())
                .log(Level.INFO, jsonString);
        
        TextMessage textMessage = context.createTextMessage(jsonString);
        textMessage.setStringProperty("method", "generateBill");
        
        context.createProducer().send(queue, textMessage);
    }
}
