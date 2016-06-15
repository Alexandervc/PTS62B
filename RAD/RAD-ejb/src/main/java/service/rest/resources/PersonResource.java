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
public class PersonResource {
    
    @Inject
    private PersonService service;

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
        
        PersonDto personDto = DtoConverter.convertPersonToDto(person);
        
        return Response.status(Response.Status.OK)
                .entity(personDto)
                .build();
    }
}
