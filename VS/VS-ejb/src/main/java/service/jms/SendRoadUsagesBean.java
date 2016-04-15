/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.RoadUsage;
import com.google.gson.Gson;
import java.util.List;
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
    
    /**
     * Send the given roadUsages via JMS to RAD.
     * @param roadUsages The roadUsages.
     * @throws JMSException 
     */
    public void sendRoadUsages(List<RoadUsage> roadUsages) throws JMSException {
        // To JSON
        Gson gson = new Gson();
        String jsonString = gson.toJson(roadUsages);
        
        TextMessage textMessage = this.context.createTextMessage(jsonString);
        textMessage.setStringProperty("method", "receiveRoadUsages");
        
        this.context.createProducer().send(this.queue, textMessage);
    }
}
