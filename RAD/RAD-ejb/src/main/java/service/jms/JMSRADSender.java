/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 *
 * @author Alexander
 */
@Stateless
public class JMSRADSender {

    @Inject
    @JMSConnectionFactory("jms/__defaultConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "jms/VS/queue")
    private Destination queue;

    public void sendGenerateRoadUsagesCommand(Long cartrackerId,
        Date beginDate, Date endDate) throws JMSException {
        MapMessage mapMessage = context.createMapMessage();
        mapMessage.setStringProperty("method", "generateRoadUsages");
        mapMessage.setLong("cartrackerId", cartrackerId);

        // Convert date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String beginDateString = df.format(beginDate);
        String endDateString = df.format(endDate);

        mapMessage.setString("beginDate", beginDateString);
        mapMessage.setString("endDate", endDateString);
        
        context.createProducer().send(queue, mapMessage);
    }

}
