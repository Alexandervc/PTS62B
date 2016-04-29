/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.soap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import service.RadService;
import service.RoadUsage;

/**
 *
 * @author Alexander
 */
@WebService(serviceName = "RadWs")
@Stateless
public class RadWs {

    @Inject
    private RadService radService;

    /**
     * Get the total price for the given roadUsages.
     *
     * @param roadUsagesJson JSON-string of the given roadUsages to get the
     * price for.
     * @return Double.
     */
    @WebMethod
    public Double getTotalPrice(@WebParam(name = "roadUsages") 
            String roadUsagesJson) {
        // Convert to roadUsages
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RoadUsage>>() {}.getType();
        List<RoadUsage> roadUsages = gson.fromJson(roadUsagesJson, type);
        
        return this.radService.getTotalPrice(roadUsages);
    }
}
