/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.websockets;

import business.MonitoringManager;
import com.google.gson.Gson;
import common.domain.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import util.TestsParser;

/**
 * Websocket client for the server. Sends monitoring updates.
 * @author Edwin
 */
@ServerEndpoint( 
        value = "/endpoint"
)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class MonitoringServerSockets {
        
    private static List<Session> clients = new ArrayList<>();
    
    @Inject
    private MonitoringManager manager;
    
    /**
     * Opens the websocket connection, adds the session to the list 
     * of clients. So messages can be send. Sends the current version
     * of the monitoring status.
     * @param session The session that gets added to the list of clients.
     * @throws IOException Throws an IOException if the websocket
     * message sending does not work.
     */
    @OnOpen
    public void open(Session session) throws IOException {
        clients.add(session);
        this.updateMonitoring();
    }
    
    /**
     * Removes the session from the list of clients.
     * @param session The session that has to be removed.
     */
    @OnClose
    public void close(Session session) {
        clients.remove(session);
    }
    
    /**
     * Sends a update message to all the clients. This contains the newest
     * version of the monitoring status's. The message contains all tests.
     * @throws IOException Throws a IOException if the message can't be send.
     */
    public void updateMonitoring() throws IOException{
        Map<String, List<Map.Entry<String, String>>> map = new HashMap();
        for(common.domain.System sys : this.manager.getSystems()) {
            List<List<Test>> tests = this.manager.retrieveTests(sys);
            List<Map.Entry<String, String>> systemTests = TestsParser
                    .parseSystemTests(tests, sys);
            map.put(sys.getName(), systemTests);
        }
        Gson gson = new Gson();
        String json =  gson.toJson(map);
        
        for(Session s : clients) {
            s.getBasicRemote().sendText(json);
        }

        
    }

}
