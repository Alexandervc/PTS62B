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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
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

    public ReceiveMissingPositionsBean(String cartrackerid, SearchMissingPosition search) {
        this.cartrackerid = cartrackerid;
        this.search = search;
        this.selector = "cartrackerid='" + cartrackerid + "'";
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
            final ConnectionFactory connectionFactory = lookup(ConnectionFactory.class, JNDI_CONNECTION_FACTORY);
            final Queue queue = lookup(Queue.class, JNDI_QUEUE);
            JMSContext jmsContext = connectionFactory.createContext();
            final JMSConsumer queueConsumer = jmsContext.createConsumer(queue, this.selector);
            final MessageListener messageListener1 = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        MapMessage mapMessage = (MapMessage) message;

                        // Get values
                        String jsonPositions = mapMessage.getString("listNumbers");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Long>>() {
                        }.getType();
                        List<Long> positions
                                = gson.fromJson(jsonPositions, type);

                        search.searchPositions(cartrackerid, positions);

                    } catch (JMSException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);

                    }
                }
            };
            queueConsumer.setMessageListener(messageListener1);
        }catch(IllegalArgumentException ex){
            LOGGER.log(Level.SEVERE, "failed to lookup instance", ex);
        }
    }
}
