/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import dto.RoadUsage;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import service.soap.RadWs;
import service.soap.RadWs_Service;

/**
 * Helper for connecting to RAD via SOAP.
 * @author Alexander
 */
@Stateless
public class RadWsService {
    // TODO set to server link
    @WebServiceRef(wsdlLocation = "http://localhost:8080/RadWs/RadWs?wsdl")
    private RadWs_Service service;

    private RadWs port;

    @PostConstruct
    private void start() {
        this.port = this.service.getRadWsPort();
    }

    /**
     * Get the total price for the given roadUsages.
     *
     * @param roadUsages The given roadUsages to get the price for.
     * @return List of RoadUsage.
     */
    public Double getTotalPrice(List<RoadUsage> roadUsages) {
        // To JSON
        Gson gson = new Gson();
        String jsonRoadUsages = gson.toJson(roadUsages);
        
        return this.port.getTotalPrice(jsonRoadUsages);
    }
}
