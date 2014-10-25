/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.resources;

import com.feed.api.constants.Sort;
import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.exceptions.ValidationException;
import com.feed.api.services.FeedService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author ruben
 */
@Path("/v1/feed")
@Produces(MediaType.APPLICATION_JSON)
public class FeedResource {

    final static Logger logger = LoggerFactory.getLogger(FeedResource.class);
    @Autowired
    private FeedService feedService;

    @GET
    public Response get(@Context UriInfo info) throws Exception {

        List<String> urls = info.getQueryParameters().get("url");
        String sort = info.getQueryParameters().getFirst("sort");

        logger.debug("/v1/feed get parameters [" + urls + "][" + sort + "]");

        FeedOptions options = buildFeedOptions(urls, sort);

        List<Feed> feeds = feedService.find(options);

        //build response
        ResponseBuilder builder = Response.ok(feeds, MediaType.APPLICATION_JSON);
        return builder.build();
    }

    //spring DI
    public void setFeedService(FeedService feedService) {
        this.feedService = feedService;
    }

    private FeedOptions buildFeedOptions(List<String> urls, String sort) throws ValidationException {

        if (urls == null) {
            throw new ValidationException("please specify a feed url");
        }
        
        Sort sortEnum = null;
        if (!StringUtils.isEmpty(sort)) {
            if (sort.equals("desc")) {
                sortEnum = Sort.DESC;
            } else if (sort.equals("round")) {
                sortEnum = Sort.ROUNDROBIN;
            }
        }
        
        if (sortEnum == null) {
            sortEnum = Sort.DESC;
        }
        
        return new FeedOptions(urls, sortEnum);
    }
}
