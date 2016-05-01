/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.RadService;
import dto.RoadUsage;

/**
 * The REST resource for totalPrice.
 * @author Alexander
 */
@Path("/totalPrice")
@Stateless
public class TotalPriceResource {

    @Inject
    private RadService radService;

    /**
     * Get the total price for the given roadUsages.
     * @param roadUsagesJson JSON-string of the given roadUsages to get the
     *      price for.
     * @return If successfull Response with status OK and Double 
     *      price as entity.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getTotalPrice(String roadUsagesJson) {
        // Convert to roadUsages
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RoadUsage>>() {}.getType();
        List<RoadUsage> roadUsages = gson.fromJson(roadUsagesJson, type);
        
        Double totalPrice = this.radService.getTotalPrice(roadUsages);
        
        return Response.status(Response.Status.OK)
                .entity(totalPrice)
                .build();
    }
}
