/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import dto.RoadUsage;
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
 * The bean that sends roadUsages to RAD.
 * @author Alexander
 */
@Stateless
public class SendRoadUsagesBean {
    @Inject
    @JMSConnectionFactory("jms/RADConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/RAD/queue")
    private Destination queue;
    
    private static final Logger LOGGER = Logger
            .getLogger(SendRoadUsagesBean.class.getName());
    
    /**
     * Send the given roadUsages via JMS to RAD.
     * @param roadUsages The roadUsages.
     */
    public void sendRoadUsages(List<RoadUsage> roadUsages) {
        try {
            // To JSON
            Gson gson = new Gson();
            String jsonString = gson.toJson(roadUsages);
            
            TextMessage textMessage = this.context
                    .createTextMessage(jsonString);
            textMessage.setStringProperty("method", "receiveRoadUsages");
            
            this.context.createProducer().send(this.queue, textMessage);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
