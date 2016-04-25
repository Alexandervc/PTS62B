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

/**
 *
 * @author Linda
 */
@MessageDriven(mappedName = "jms/VS/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "messageSelector",
            propertyValue = "method='getFunctionalStatus'")
})
public class ReceiveTestRequestBean implements MessageListener {

    @Inject
    private SendTestResultsBean sender;

    /**
     * receives request for testresult from LMS
     * start JUnit test inside this system
     * @param message contains request
     */
    @Override
    public void onMessage(Message message) {
//        try {
//            MapMessage mapMessage = (MapMessage) message;
//            
//            mapMessage.getString("method");
//            
//            // engine for test
//            JUnitCore engine = new JUnitCore();
//            engine.addListener(new TextListener(System.out)); // required to print reports
//
//            // run test RoadUsageTest
//            Result result = engine.run(junitTest.RoadUsageTest.class);
//            // TODO carPositionManagerTest
//            
//            // sender will be send to LMS
//            sender.sendTestResults(result);
//        } catch (JMSException ex) {
//            Logger.getLogger(ReceiveTestRequestBean.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
    }

}
