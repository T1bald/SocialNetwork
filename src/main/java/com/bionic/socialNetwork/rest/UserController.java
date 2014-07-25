package com.bionic.socialNetwork.rest;

import com.bionic.socialNetwork.logic.lists.FollowingUsersList;
import com.bionic.socialNetwork.logic.UserLogic;
import com.bionic.socialNetwork.logic.lists.InterestList;
import com.bionic.socialNetwork.logic.lists.PostsList;
import com.bionic.socialNetwork.logic.lists.ReceivedMessageList;
import com.bionic.socialNetwork.models.User;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Dmytro Troshchuk, Igor Kozhevnikov, Denis Biyovskiy
 * @version 1.00  18.07.14.
 */


@Path("user{id}")
public class UserController {
    @GET
    @Path("interests")
    @Produces(MediaType.APPLICATION_JSON)
    public InterestList getInterests(@PathParam("id") long id) {
        return new InterestList(id);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream getPage(@Context ServletContext context) {
            return context.getResourceAsStream("/WEB-INF/pages/user.html");


    }

    @GET
    @Path("getUser")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") long id) {
        return new UserLogic().getUser(id);
    }

    @POST
    @Path("createPost")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addPost(@PathParam("id") long id,
                           @FormParam("msg") String msg) {
        return new UserLogic().createPost(id, msg);
    }

    @GET
    @Path("posts{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public PostsList getPosts(@PathParam("id") long id,
                              @PathParam("page") int page) {
        return new PostsList(id, page);

    }

    @GET
    @Path("followings{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public FollowingUsersList getFollowingUser(@PathParam("id") long id,
                                               @PathParam("page") int page) {

        return new FollowingUsersList(id, page);

    }

    @GET
    @Path("received_messages{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReceivedMessageList getPrivateMessage(@PathParam("id") int id,
                                                 @PathParam("page") int page) {

        ReceivedMessageList receivedMessageList =
                new ReceivedMessageList(id, page);
        return receivedMessageList;

    }

    //Create empty rest for News, Followings, Private message, Groups
    @GET
    @Path("news")
    @Produces(MediaType.APPLICATION_JSON)
    public PostsList getNews(@Context HttpServletRequest request,
                             @PathParam("number") int number) {
        return null;
    }

    @GET
    @Path("home")
    @Produces(MediaType.APPLICATION_JSON)
    public PostsList getHomePage(@Context HttpServletRequest request,
                                 @PathParam("number") int number) {
        return null;
    }

    @GET
    @Path("groups")
    @Produces(MediaType.APPLICATION_JSON)
    public PostsList getGroups(@Context HttpServletRequest request,
                               @PathParam("number") int number) {
        return null;
    }

    @GET
    @Path("exit")
    @Produces(MediaType.APPLICATION_JSON)
    public String exit(@Context HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", "0");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "{\"status\": true}";
    }
}
