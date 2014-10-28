/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.resources;

import com.feed.api.constants.AppConstants;
import com.feed.api.domain.Group;
import com.feed.api.exceptions.ValidationException;
import com.feed.api.services.GroupService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ruben
 */
@Path("/v1/group")
@Produces(AppConstants.CONTENT_TYPE_JSON_UTF_8)
public class GroupResource {

    final static Logger logger = LoggerFactory.getLogger(GroupResource.class);
    @Autowired
    private GroupService groupService;

    @POST
    public Response post(Group model) throws Exception {

        Group created = groupService.create(model);
        // 201
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .header(AppConstants.HEADER_LOCATION, AppConstants.GROUP_PATH + "/" + created.getId()).build();

    }

    @GET
    public Response get(@Context UriInfo info) throws Exception {


        List<Group> list = groupService.find();

        //build response
        return Response.ok(list).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id, @Context Request request) throws Exception {

        Integer groupId = validateId(id);

        Group item = groupService.find(groupId);

        //200 build response
        return Response.ok(item).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") String id, Group model, @Context Request request)
            throws Exception {

        Integer groupId = validateId(id);
        
        Group found = groupService.find(groupId);

        if (found == null) {

            //create
            Group created = groupService.create(model);
            // 201
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .header(AppConstants.HEADER_LOCATION, AppConstants.GROUP_PATH + "/" + created.getId()).build();

        } else {

            
            model.setId(groupId);
            
            Group updated = groupService.update(model);

            // 200
            return Response.status(Response.Status.OK).entity(updated).build();

        }

    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) throws Exception {
        
        Integer groupId = validateId(id);

        groupService.delete(groupId);

        // 204
        return Response.status(Response.Status.NO_CONTENT).build();

    }
    
    
    @DELETE
    public Response deleteAll() throws Exception {
        
        groupService.deleteAll();

        // 204
        return Response.status(Response.Status.NO_CONTENT).build();

    }
    
    private Integer validateId(String id) throws ValidationException {
        try {
             return Integer.valueOf(id);
        } catch (Exception e) {
            throw new ValidationException("please specify a valid id: " + id );
        }
    }
    
    //spring DI
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}
