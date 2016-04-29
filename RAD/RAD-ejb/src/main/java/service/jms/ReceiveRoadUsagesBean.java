/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import service.RadService;
import service.RoadUsage;

/**
 * Generate Bill Bean. 
 * @author Alexander.
 */
@MessageDriven(mappedName="jms/RAD/queue", activationConfig={
    @ActivationConfigProperty(propertyName="messageSelector", 
            propertyValue="method='receiveRoadUsages'")
})
public class ReceiveRoadUsagesBean implements MessageListener {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveRoadUsagesBean.class.getName());
    
    @Inject
    private RadService radService;
    
    
    /**
     * Receives roadUsages from VS.
     * @param message A TextMessage containing a jsonString with roadUsages.
     */
    @Override
    public void onMessage(Message message) {
        try {
            // Get roadusages from message
            TextMessage textMessage = (TextMessage) message;
            String jsonString = textMessage.getText();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<RoadUsage>>() {}.getType();
            List<RoadUsage> roadUsages = gson.fromJson(jsonString, type);
            
            // Call service
            this.radService.receiveRoadUsages(roadUsages);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
}
