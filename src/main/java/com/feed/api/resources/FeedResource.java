/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.resources;

import com.feed.api.constants.AppConstants;
import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.services.FeedService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ruben
 */
@Path("/v1/feed")
@Produces(AppConstants.CONTENT_TYPE_JSON_UTF_8)
public class FeedResource {

    final static Logger logger = LoggerFactory.getLogger(FeedResource.class);
    @Autowired
    private FeedService feedService;

    @GET
    public Response get(@Context UriInfo info) throws Exception {

        List<String> urls = info.getQueryParameters().get(AppConstants.PARAM_URL);
        String sort = info.getQueryParameters().getFirst(AppConstants.PARAM_SORT);

        logger.debug("/v1/feed get parameters [" + urls + "][" + sort + "]");

        FeedOptions options = new FeedOptions(urls, sort);

        List<Feed> feeds = feedService.find(options);

        //build response
        return Response.ok(feeds).build();
    }

    //spring DI
    public void setFeedService(FeedService feedService) {
        this.feedService = feedService;
    }
}
