/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.junit.runner.Result;

/**
 *
 * @author Linda
 */
@Stateless
public class SendTestResultsBean {
    @Inject
    @JMSConnectionFactory("jms/LMSConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup="jms/LMS/queue")
    private Destination queue;
    
    public void sendTestResults(Result result)  throws JMSException{
        
        String wasSucces = Boolean.toString(result.wasSuccessful());
        TextMessage textMessage = this.context.createTextMessage(wasSucces);
        
        textMessage.setStringProperty("method", "receiveTestresultsVS");
        
        this.context.createProducer().send(this.queue, textMessage);
    }
    
}
