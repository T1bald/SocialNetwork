package com.bionic.socialNetwork.rest;

import com.bionic.socialNetwork.logic.GroupLogic;
import com.bionic.socialNetwork.logic.lists.GroupPostList;
import com.bionic.socialNetwork.models.Group;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Dmytro Troshchuk
 * @version 1.00  23.07.14.
 */
@Path("group{id}")
public class GroupController {
    @Context
    private ServletContext context;
    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context HttpServletRequest request,
                               @PathParam("id") long id) {
        return context.getResourceAsStream("/WEB-INF/pages/group.html");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("description")
    public Group getDescription(@PathParam("id") long id) {
        return new GroupLogic().getGroup(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("posts{page}")
    public GroupPostList getPosts(@PathParam("id") long id,
                                  @PathParam("page") int page) {
        return new GroupPostList(id, page);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("createPost")
    public boolean createPost(@Context HttpServletRequest request,
                              @PathParam("id") long id,
                              @FormParam("msg") String msg) {
        return new GroupLogic()
                .createPost(id, (Long) request.getAttribute("id"), msg);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("deletePost")
    public boolean deletePost(@Context HttpServletRequest request,
                              @PathParam("id") long id,
                              @FormParam("postId") long postId) {
        return new GroupLogic()
                .deletePost((Long) request.getAttribute("id"), postId);
    }


}