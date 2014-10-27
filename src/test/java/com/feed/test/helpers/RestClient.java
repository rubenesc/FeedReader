/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.helpers;

import com.feed.api.constants.AppConstants;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
        return get(url, null);
    }

    public Response get(String url, Multimap<String, String> queryParams) throws Exception {

        WebTarget webTarget = client.target(url);
        
        webTarget = setQueryParams(webTarget, queryParams);

        Invocation.Builder request = webTarget.request();

        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        Response response = request.get();
        return response;

    }

    private WebTarget setQueryParams(WebTarget webTarget, Multimap<String, String> queryParams) {
        
        if (queryParams != null) {
            
            // get all the set of keys
            Set<String> keys = queryParams.keySet();

            for (String key : keys) {
                Collection<String> values = queryParams.get(key);
                for (String value : values) {
                    webTarget = webTarget.queryParam(key, value);
                    
                }
            }

        }
        
        return webTarget;
    }
    
    
    
    public void close(){
        if (this.client != null){
            this.client.close();
        }
    
    }
}
