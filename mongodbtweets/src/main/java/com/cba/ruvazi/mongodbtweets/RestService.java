/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cba.ruvazi.mongodbtweets;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("twitter")
public class RestService {

    Gson gson;
    MongoDBAccessor mdba = new MongoDBAccessor("mongodb://localhost:27017","social_net","tweets");

    @Context
    private UriInfo context;

   
    public RestService() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    @GET
    @Path("apitest")
    public String apitest() {
        return "Hey";
    }

    /**
     * Retrieves representation of an instance of api.RestService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("distinct")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDistinctUserCount() {
        
        gson.toJson(mdba.getDistinctUserCount());

        return Response.status(Response.Status.OK).entity("" + mdba.getDistinctUserCount()).build();
    }

    @GET
    @Path("topusers")
    public Response getTopUsers() {

        return Response.status(Response.Status.OK).entity(gson.toJson(mdba.getTopUsers())).build();

    }
    @GET
    @Path("grump")
    public Response getGrumpiest() {

        return Response.status(Response.Status.OK).entity(gson.toJson(mdba.getGrumpiestUsers())).build();

    }
    @GET
    @Path("happy")
    public Response getHappiest() {

        return Response.status(Response.Status.OK).entity(gson.toJson(mdba.getHappiestUsers())).build();

    }
    @GET
    @Path("mentioned")
    public Response getMostMentioned() throws Exception {

        return Response.status(Response.Status.OK).entity(gson.toJson(mdba.getTopMentionedUsers())).build();

    }
    @GET
    @Path("mentioning")
    public Response getMostMentioning() {

        return Response.status(Response.Status.OK).entity(gson.toJson(mdba.getTopMentioningUsers())).build();

    }

}
