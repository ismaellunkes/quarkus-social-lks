package br.lks.quarkussocial.rest;

import br.lks.quarkussocial.rest.dto.*;
import br.lks.quarkussocial.services.*;
import jakarta.inject.*;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final PostService postService;

    @Inject
    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @POST
    public Response create (CreatePostRequest createPostRequest) throws ConstraintViolationException {
        PostRequest postCreated;
        try {
            postCreated = postService.create(createPostRequest);
        } catch (ErrorsValidatorException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getErrors()).build();
        }
        return Response.ok().entity(postCreated).build();
    }

    @GET
    @Path("{idUser}")
    public Response listPostByUser(@PathParam("idUser") Long idUser) {
        List<PostRequest> posts = postService.listPostsByUser(idUser);
        return Response.ok(posts).build();
    }

    @GET
    @Path("/timeline/{idUser}")
    public List<PostTimelineRequest> listPostsTimeLineByFollower(@PathParam("idUser") Long idUser) {
        return postService.listPostsTimeLineByIdUser(idUser);
    }


}
