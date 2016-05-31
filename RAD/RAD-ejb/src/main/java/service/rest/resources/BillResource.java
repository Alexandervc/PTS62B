/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.Bill;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.BillService;

/**
 * The REST resource for Bills.
 * @author Jesse
 */
@Path("/persons/{personId}/bills")
@Stateless
public class BillResource {
    private static final Logger LOGGER
            = Logger.getLogger(BillResource.class.getName());

    @Inject
    private BillService billService;
    
    /**
     * Get the bill which is generated for each car of the given person.
     * @param personId The id of the user to generate the bill for.
     * @param month The number of the month to generate the bill for.
     * @param year The number of the year to generate the bill for.
     * @return A list of Bills, one Bill for each car of the person.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBill(
            @PathParam("personId") long personId,
            @QueryParam("month") int  month, 
            @QueryParam("year")  int  year) {
        try {
            List<Bill> billsPerCar = this.billService.generateBill(
                    personId, 
                    month, 
                    year);
            
            // Set the person of the bill to null in order to prevent too large
            // JSON strings.
            for (Bill bill : billsPerCar) {
                bill.setPerson(null);
            }

            return Response.status(Response.Status.OK)
                    .entity(billsPerCar)
                    .build();
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, "Person " + personId + " not found.", ex);
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
