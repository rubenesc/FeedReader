/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api;

import com.feed.api.exceptions.mapper.GenericExceptionMapper;
import com.feed.api.exceptions.mapper.ValidationExceptionMapper;
import com.feed.api.resources.FeedResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author ruben
 */
public class FeedApiApp extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public FeedApiApp() {

        // register application resources
        register(FeedResource.class);

        registerExceptionMappers();

        registerFeatures();

    }

    // register exception mappers
    private void registerExceptionMappers() {
        register(GenericExceptionMapper.class);
        register(ValidationExceptionMapper.class);
    }

    // register features
    private void registerFeatures() {
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        register(EntityFilteringFeature.class);
    }
}
