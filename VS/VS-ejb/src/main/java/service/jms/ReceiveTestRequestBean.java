/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runners.AllTests;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='receiveTestRequest'")
})
public class ReceiveTestRequestBean implements MessageListener {

    @Inject
    private SendTestResultsBean sender;

    @Override
    public void onMessage(Message message) {
        try {
            
            JUnitCore engine = new JUnitCore();
            engine.addListener(new TextListener(System.out)); // required to print reports

            Result result = engine.run(junitTest.RoadUsageTest.class);
            
            sender.sendTestResults(result);
        } catch (Exception ex) {

        }
    }

}
