/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.api;

import com.feed.api.constants.AppConstants;
import com.feed.api.domain.Group;
import com.feed.api.services.GroupService;
import com.feed.test.helpers.FeedTestHelper;
import com.feed.test.helpers.RestClient;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ruben
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-applicationContext.xml"})
public class GroupApiIT {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GroupApiIT.class);
    @Autowired
    GroupService groupService;
    RestClient restClient = null;

    @Before
    public void setUp() throws Exception {
        restClient = new RestClient();
    }

    @After
    public void cleanUp() throws Exception {
        restClient.close();
    }

    @Test
    public void tests() throws Exception {

        try {
            this.testDeleteAll();

            this.testFindNone();

            this.testCreate();

            this.testFindAll();
            this.testNotFound();

            this.testUpdate();

            this.testDelete();
            this.testInvalidDelete();

            this.testFindAll();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    private void testFindNone() throws Exception {
        
        Response response = restClient.get(AppConstants.GROUP_PATH);

        Assert.assertEquals(200, response.getStatus());
        List<Group> list = response.readEntity(new GenericType<List<Group>>() {
        });

        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
        
        
    }

    private void testCreate() throws Exception {

        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");
        urls.add("http://metallica.tumblr.com/rss");
        urls.add("http://blog.bandpage.com/feed/");

        Group model = new Group("Group 1", urls);

        //create item
        Response responsePost = restClient.post(AppConstants.GROUP_PATH, model);
        Assert.assertEquals(201, responsePost.getStatus());
        Assert.assertNotNull("Missing location on POST response", responsePost.getLocation());
        Group created = responsePost.readEntity(Group.class);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        FeedTestHelper.verifyItemMatch(model, created);

        //find it and verify
        String itemUrl = AppConstants.GROUP_PATH.concat("/").concat(created.getId().toString());
        Response responseGet = restClient.get(itemUrl);
        Assert.assertEquals(200, responseGet.getStatus());
        Group found = responseGet.readEntity(Group.class);
        FeedTestHelper.verifyItemMatch(created, found);

    }

    private void testFindAll() throws Exception {

        //get all Items
        Response response = restClient.get(AppConstants.GROUP_PATH);

        Assert.assertEquals(200, response.getStatus());
        List<Group> list = response.readEntity(new GenericType<List<Group>>() {
        });

        Assert.assertNotNull(list);
        
        logger.info("Find all groups: ");
        for (Group group : list) {
            logger.info(group.toString());
        }
        
    }

    private void testNotFound() throws Exception {

        //make sure it doesn't exist anymore
        Response response = restClient.get(AppConstants.GROUP_PATH.concat("/").concat("1234"));
        Assert.assertEquals(404, response.getStatus());
        
    }

    private void testUpdate() throws Exception {

        //create 
        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");

        Group model = new Group("Group 2", urls);

        //create item
        Response responsePost = restClient.post(AppConstants.GROUP_PATH, model);
        Assert.assertEquals(201, responsePost.getStatus());
        Group created = responsePost.readEntity(Group.class);
        
        //update item
        created.setName(created.getName() + " - updated");
        created.getUrls().add("http://metallica.tumblr.com/rss");
        
        String itemUrl = AppConstants.GROUP_PATH.concat("/").concat(created.getId().toString());
        Response responsePut = restClient.put(itemUrl, created);
        Assert.assertEquals(200, responsePut.getStatus());
        Group updated = responsePut.readEntity(Group.class);
        FeedTestHelper.verifyItemMatch(created, updated);
        
        //find and verify
        Response responseGet = restClient.get(itemUrl);
        Assert.assertEquals(200, responseGet.getStatus());
        Group found = responseGet.readEntity(Group.class);
        FeedTestHelper.verifyItemMatch(created, found);

    }
    
    private void testDeleteAll() throws Exception {
        //delete 
        Response responseDelete = restClient.delete(AppConstants.GROUP_PATH);
        Assert.assertEquals(204, responseDelete.getStatus());
    }
    

    private void testDelete() throws Exception {

        //create 
        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");

        Group model = new Group("Group 3", urls);

        //create item
        Response responsePost = restClient.post(AppConstants.GROUP_PATH, model);
        Assert.assertEquals(201, responsePost.getStatus());
        Group created = responsePost.readEntity(Group.class);

        //find it
        String itemUrl = AppConstants.GROUP_PATH.concat("/").concat(created.getId().toString());
        Response responseGet = restClient.get(itemUrl);
        Assert.assertEquals(200, responseGet.getStatus());
        
        //delete 
        Response responseDelete = restClient.delete(itemUrl);
        Assert.assertEquals(204, responseDelete.getStatus());

        //make sure it doesn't exist anymore
        responseGet = restClient.get(itemUrl);
        Assert.assertEquals(404, responseGet.getStatus());
        
    }

    private void testInvalidDelete() {
        String itemUrl = AppConstants.GROUP_PATH.concat("/").concat("-1");
        Response responseDelete = restClient.delete(itemUrl);
        Assert.assertEquals(204, responseDelete.getStatus());
    }
}
