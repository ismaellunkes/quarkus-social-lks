package br.lks.quarkussocial.rest;

import br.lks.quarkussocial.rest.dto.*;
import br.lks.quarkussocial.services.*;
import jakarta.inject.*;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerService followerService;

    @Inject
    public FollowerResource(FollowerService followerService) {
        this.followerService = followerService;
    }

    @POST
    @Path("{idUser}")
    public Response create (@PathParam("idUser")Long idUser, CreateFollowerRequest createFollowerRequest) throws ConstraintViolationException {
        try {
            followerService.create(idUser, createFollowerRequest);
            return Response.ok().build();
        } catch (ErrorsValidatorException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getErrors()).build();
        }
    }

    @GET
    @Path("{idUser}")
    public Response listFollwersByUser(@PathParam("idUser") Long idUser) {
        List<FollowerRequest> followers = followerService.listFollowersByUser(idUser);
        return Response.ok(followers).build();
    }

}
