/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Alexander
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig={
    @ActivationConfigProperty(propertyName="messageSelector", propertyValue = "method='receiveCarpositions'")
})
public class ReceiveCarpositionsBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
        
    }
    
}
