/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Person;
import static dto.DtoConverter.convertPersonToDto;
import dto.LoginUserDto;
import dto.PersonDto;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.PersonService;

/**
 *
 * @author Linda
 */
@Path("/login")
@Stateless
public class PersonResource {

    private static final Logger LOG = Logger.
            getLogger(PersonResource.class.getName());
    
    @Inject
    private PersonService service;

    /**
     * Get the person with the given password and username.
     *
     * @param loginUserJson Json-string with username and password.
     * @return If successfull Response with OK and person object;
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String loginUserJson) {
        // read from Json.
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LoginUserDto>>() {
        }.getType();
        List<LoginUserDto> loginUser = gson.fromJson(loginUserJson, type);
        Person person = null;
        // search for Person.
        if (!loginUser.isEmpty()) {
            person = this.service.findPersonLogin(loginUser.get(0)
                    .getUsername(), loginUser.get(0).getPassword());
        }

        // create Json-string from person.
        if (person != null) {
            return Response.status(Response.Status.OK)
                    .entity(person.getId())
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(null)
                .build();
    }

    @Path("/{userid}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("userid") String userid) {
        // read from Json.
        Gson gson = new Gson();

        Person person = null;

        Long l = null;
        try {
            l = Long.parseLong(userid);
            System.out.println("long l = " + l);
        } catch (NumberFormatException nfe) {
            l = null;
            LOG.log(Level.INFO, null, nfe);
        }

        // search for Person.
        List<PersonDto> personlist = new ArrayList<>();
        if (l != null) {
            person = this.service.findPersonById(l);

            PersonDto dto = convertPersonToDto(person);

            personlist.add(dto);
        }
        // create Json-string from person.
        String personJson = gson.toJson(personlist);

        return Response.status(Response.Status.OK)
                .entity(personJson)
                .build();
    }
}
