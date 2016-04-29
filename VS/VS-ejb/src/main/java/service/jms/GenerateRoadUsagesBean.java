/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import dto.RoadUsage;
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
 * The messagedriven bean for receiving commands from RAD 
 * to generate roadUsages.
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
    private SendRoadUsagesBean roadUsagesSender;
    
    private static final Logger LOGGER = Logger
            .getLogger(GenerateRoadUsagesBean.class.getName());
    
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            String cartrackerId = mapMessage.getString("cartrackerId");
            String beginDateString = mapMessage.getString("beginDate");
            String endDateString = mapMessage.getString("endDate");
            
            // Convert dates
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date beginDate = df.parse(beginDateString);
            Date endDate = df.parse(endDateString);
            
            // Generate road usages
            List<RoadUsage> roadUsages = this.roadUsageService
                    .generateRoadUsages(cartrackerId, beginDate, endDate);
            
            // Send
            this.roadUsagesSender.sendRoadUsages(roadUsages);
        } catch (JMSException | ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
