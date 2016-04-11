/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.RoadUsage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import service.RoadUsageService;

/**
 *
 * @author Alexander
 */
@MessageDriven(mappedName="jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector", 
            propertyValue = "method='generateRoadUsages'")
})
public class GenerateRoadUsagesBean implements MessageListener {
    @Inject
    private RoadUsageService roadUsageService;
    
    @Inject
    private JMSVSSender vsSender;
    
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            Long cartrackerId = mapMessage.getLong("cartrackerId");
            String beginDateString = mapMessage.getString("beginDate");
            String endDateString = mapMessage.getString("endDate");
            
            // Convert dates
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date beginDate = df.parse(beginDateString);
            Date endDate = df.parse(endDateString);
            
            // Generate road usages
            List<RoadUsage> roadUsages = this.roadUsageService
                    .generateRoadUsages(cartrackerId, beginDate, endDate);
            Logger.getLogger(GenerateRoadUsagesBean.class.getName())
                    .log(Level.INFO, String.valueOf(roadUsages.size()));
            
            // Send
            vsSender.sendRoadUsages(roadUsages);
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(GenerateRoadUsagesBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}