/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.helpers;

import com.feed.api.domain.Group;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 *
 * Handles Rest Operations (POST, PUT, GET, DELETE)
 *
 * @author ruben
 */
public class RestClient {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(RestClient.class);
    public Client client;
    public String BASE_URL = "http://localhost:8080";
    public String CONTENT_TYPE = "Content-type";
    private String API_URL;

    /**
     *
     * @param resourcePath ex: /feed-reader/v1/feed /feed-reader/v1/group
     * @throws Exception
     */
    public RestClient() throws Exception {


        // ex: http://localhost:8080/feed-reader/v1/feed
        // ex: http://localhost:8080/feed-reader/v1/group
//        API_URL = BASE_URL + resourcePath;
        API_URL = BASE_URL; //+ resourcePath;

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);

        client = ClientBuilder.newClient(clientConfig);
    }

    //GET
    public Response get(String url) throws Exception {
        return get(url, null);
    }

    public Response get(String url, Multimap<String, String> queryParams) throws Exception {

        WebTarget webTarget = client.target(API_URL.concat(url));

        webTarget = setQueryParams(webTarget, queryParams);

        Invocation.Builder request = webTarget.request();

        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        logger.info("GET [" + webTarget.getUri().toString() + "] ...");

        return request.get();
    }

    //POST
    public Response post(String url, Object item) throws Exception {
        return post(url, item, null);
    }

    public Response post(String url, Object item, Map<String, String> headers) {

        WebTarget webTarget = client.target(API_URL.concat(url));

        Invocation.Builder request = webTarget.request();
        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        
        logger.info("POST [" + webTarget.getUri().toString() + "] ...");

        return request.post(Entity.entity(item, MediaType.APPLICATION_JSON));

    }

    //UPDATE
    public Response put(String url, Object item) {
        return put(url, item, null);
    }

    public Response put(String url, Object item, Map<String, String> headers) {

        WebTarget webTarget = client.target(API_URL.concat(url));

        Invocation.Builder request = webTarget.request();
        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }

        logger.info("PUT [" + webTarget.getUri().toString() + "] ...");

        return request.put(Entity.entity(item, MediaType.APPLICATION_JSON));
    }

    //DELETE
    public Response delete(String url) {

        WebTarget webTarget = client.target(API_URL.concat(url));

        Invocation.Builder request = webTarget.request();
        request.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);

        logger.info("DELETE [" + webTarget.getUri().toString() + "] ...");

        return request.delete();
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

    public void close() {
        if (this.client != null) {
            this.client.close();
        }

    }

}
