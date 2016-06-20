/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.Bill;
import dto.BillDto;
import dto.DtoConverter;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("/cartrackers/{cartrackerId}/bill")
@Stateless
public class BillResource extends BaseResource {

    @Inject
    private BillService billService;

    /**
     * Instantiates the BillResource.
     */
    public BillResource() {
        super();
    }
    
    /**
     * Get the bill for a car, identified by cartrackerId.
     * @param cartrackerId The id of the cartracker to generate the bill for.
     * @param month The number of the month to generate the bill for.
     * @param year The number of the year to generate the bill for.
     * @return A list of Bills, one Bill for each car of the person.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBill(@PathParam("cartrackerId") String cartrackerId,
                            @QueryParam("month") int month, 
                            @QueryParam("year") int year) {
        Bill bill = this.billService.generateBill(cartrackerId, 
                                                  month, 
                                                  year);
    
        BillDto billDto = DtoConverter.convertBillToBillDto(bill);
        String encrypted = this.encrypt(this.gson.toJson(billDto));

        if (encrypted != null) {
            return Response.status(Response.Status.OK)
                           .entity(encrypted)
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .build();
        }
    }
}
