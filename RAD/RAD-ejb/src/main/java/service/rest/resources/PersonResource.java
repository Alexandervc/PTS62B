/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.Person;
import static dto.DtoConverter.convertPersonToDto;
import dto.PersonDto;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.PersonService;

/**
 *
 * @author Linda
 */
@Path("/inlog/{username}/{password}/person")
@Stateless
public class PersonResource {

    @Inject
    private PersonService service;

    /**
     * Get inlog Person.
     * @param username of person.
     * @param password of person.
     * @return Response, with person.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String username,
            @PathParam("password") String password) {
        Person person = this.service.findPersonLogin(username, password);
        PersonDto dto = convertPersonToDto(person);

        return Response.status(Response.Status.OK)
                .entity(dto)
                .build();
    }
}
