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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import util.JsonParser;

/**
 *
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
    
    @OnOpen
    public void open(Session session) throws IOException {
        System.out.println("aids");
        clients.add(session);
        this.updateMonitoring();
    }
    
    @OnClose
    public void close(Session session) {
                System.out.println("aids");

        clients.remove(session);
    }
    
    public void updateMonitoring() throws IOException{
        Map<String, List<Map.Entry<String, String>>> map = new HashMap();
        for(common.domain.System sys : manager.getSystems()) {
            List<List<Test>> tests = manager.retrieveTests(sys);
            List<Map.Entry<String, String>> systemTests = JsonParser
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
