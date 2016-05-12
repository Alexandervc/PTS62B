/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.ForeignCountryRide;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.RadService;

/**
 * The REST resource for ForeignCountryRides.
 * @author Jesse
 */
@Path("/foreignCountryRides")
@Stateless
public class ForeignCountryRideResource {
    private static final Logger LOGGER =
            Logger.getLogger(ForeignCountryRideResource.class.getName());
    
    @Inject
    private RadService radService;
    
    /**
     * Add a foreign country ride to the database, this stores the total price
     * of the ride.
     *
     * @param input The ForeignCountryRide to store.
     * @return The response of the request.
     */
    @POST
    public Response addForeignCountryRide(final ForeignCountryRide input) {
        try {
            this.radService.addForeignCountryRide(
                    input.getForeignCountryRideId(), 
                    input.getTotalPrice());
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
