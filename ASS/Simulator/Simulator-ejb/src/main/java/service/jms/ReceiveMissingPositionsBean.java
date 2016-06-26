/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.jms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import service.SearchMissingPosition;

/**
 *
 * @author Linda
 */
public class ReceiveMissingPositionsBean implements Runnable {

    private static final Logger LOGGER = Logger
            .getLogger(ReceiveMissingPositionsBean.class.getName());

    private static final String JNDI_CONNECTION_FACTORY = "jms/VSConnectionFactory";
    private static final String JNDI_QUEUE = "jms/ASS/queue";

    private final String cartrackerid;
    private final String selector;
    private final SearchMissingPosition search;
    private Boolean run;

    public ReceiveMissingPositionsBean(String cartrackerid, SearchMissingPosition search) {
        this.cartrackerid = cartrackerid;
        this.search = search;
        this.selector = "cartrackerid='" + cartrackerid + "'";
        this.run = true;
    }

    /**
     * @param <T> the return type
     * @param retvalClass the returned value's {@link Class}
     * @param jndi the JNDI path to the resource
     * @return the resource at the specified {@code jndi} path
     */
    private static <T> T lookup(Class<T> retvalClass, String jndi) {
        try {
            return retvalClass.cast(InitialContext.doLookup(jndi));
        } catch (NamingException ex) {
            throw new IllegalArgumentException("failed to lookup instance of " + retvalClass + " at " + jndi, ex);
        }
    }

    @Override
    public void run() {
        try {
            QueueConnectionFactory qconFactory = lookup(QueueConnectionFactory.class, JNDI_CONNECTION_FACTORY);
            QueueConnection qcon = qconFactory.createQueueConnection();
            QueueSession qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue1 = lookup(Queue.class, JNDI_QUEUE);
            QueueReceiver qreceiver = qsession.createReceiver(queue1, this.selector);
            qcon.start();
            while (run) {
                MapMessage mapMessage = (MapMessage) qreceiver.receive();

                // Get values
                String jsonPositions = mapMessage.getString("listNumbers");

                Gson gson = new Gson();
                Type type = new TypeToken<List<Long>>() {
                }.getType();
                List<Long> positions
                        = gson.fromJson(jsonPositions, type);
                LOGGER.log(Level.INFO, "recieved positions for "
                        + "cartrackeris = " + cartrackerid
                        + " are " + positions);

                search.searchPositions(cartrackerid, positions);
            }

            qreceiver.close();
            qsession.close();
            qcon.close();

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "failed to lookup instance", ex);
        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("ended2");
    }

//    final ConnectionFactory connectionFactory = lookup(ConnectionFactory.class, JNDI_CONNECTION_FACTORY);
//            final Queue queue = lookup(Queue.class, JNDI_QUEUE);
//            Connection connection = connectionFactory.createConnection();
//           // Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Session session = connection.createSession();
//            //MessageConsumer queueConsumer = session.createConsumer(queue,this.selector);
//            MessageConsumer queueConsumer = session.createConsumer(queue);
//            final MessageListener messageListener1 = new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    LOGGER.log(Level.FINE, "recieved message");
//                    try {
//                        MapMessage mapMessage = (MapMessage) message;
//
//                        // Get values
//                        String jsonPositions = mapMessage.getString("listNumbers");
//
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<List<Long>>() {
//                        }.getType();
//                        List<Long> positions
//                                = gson.fromJson(jsonPositions, type);
//                        LOGGER.log(Level.FINE, "recieved positions for "
//                                + "cartrackeris = " + cartrackerid + 
//                                " are " + positions);
//
//                        search.searchPositions(cartrackerid, positions);
//
//                    } catch (JMSException ex) {
//                        LOGGER.log(Level.SEVERE, null, ex);
//
//                    }
//                }
//            };
//            queueConsumer.setMessageListener(messageListener1);
}
