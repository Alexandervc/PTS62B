/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import business.MonitoringManager;
import common.domain.TestType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Edwin
 */
public class CheckListenerCreater implements Runnable {
    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/LMS/monitoringTopic")
    private Destination queue;
    
    @Inject
    private MonitoringManager manager;

      
    @Override
    public void run() {
        final JMSConsumer topicConsumer = context.createConsumer(queue);
        final MessageListener messageListener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                MapMessage mMessage = (MapMessage) message;
                try {
                    String systemName = mMessage.getString("SystemName");
                    common.domain.System sys = manager.
                            retrieveSystemByName(systemName);
                    boolean result = mMessage.getBoolean("result");
                    TestType testType = TestType.FUNCTIONAL;                    
                } catch (JMSException ex) {
                    Logger.getLogger(CheckListenerCreater.class.getName()).log(Level.SEVERE, null, ex);
                }              
            }
        };
        topicConsumer.setMessageListener(messageListener);
    }
    
}
