/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.Person;
import dto.DtoConverter;
import dto.PersonDto;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.PersonService;

/**
 * The Rest resource for person.
 * @author Alexander.
 */
@Path("/persons/{username}")
@Stateless
public class PersonResource extends BaseResource {
    
    @Inject
    private PersonService service;

    /**
     * Instantiates the PersonResource class.
     */
    public PersonResource() {
        super();
    }
    
    /**
     * Get the person with the given username
     *
     * @param username The username of the person.
     * @return The found person.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("username") String username) {        
        // Search person
        Person person = this.service.findPersonByUsername(username);
        
        if(person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        
        // Encrypt the message.
        PersonDto personDto = DtoConverter.convertPersonToDto(person);
        String encrypted = this.encrypt(this.gson.toJson(personDto));

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
