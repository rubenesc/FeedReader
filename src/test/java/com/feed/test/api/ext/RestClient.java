/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.api.ext;

import com.feed.api.constants.AppConstants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * Handles Rest Operations (POST, PUT, GET, PATH, DELETE)
 *
 * @author ruben
 */
public class RestClient {

    public Client client;
    public String BASE_URL = "http://localhost:8080";
    public String FEED_API_URL;
    public String CONTENT_TYPE = "Content-type";

    public RestClient() {
        // ex: http://localhost:8080/feed-reader/v1/feed
        FEED_API_URL = BASE_URL + AppConstants.FEED_PATH;

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);

        client = ClientBuilder.newClient(clientConfig);
    }

    //GET

    public Response get(String url) throws Exception {
        
        WebTarget webTarget = client.target(url);
        
        Invocation.Builder request = webTarget.request();
        
        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        Response response = request.get();
        return response;

    }
}
