/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.api;

import com.feed.api.constants.AppConstants;
import com.feed.api.domain.Feed;
import com.feed.api.domain.Group;
import com.feed.test.helpers.RestClient;
import com.feed.test.helpers.FeedTestHelper;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author ruben
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-applicationContext.xml"})
public class FeedApiIT {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedApiIT.class);
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
            
        testFindFeeds();
        testFindFeedsSortAsc();
        testFindFeedsSortDesc();
        testFindFeedsSortRound();
        
        testFindFeedsInvalidSort();
        
            testFindFeedsByGroup();
//        testFindFeedsByGroupSortAsc();
//        testFindFeedsByGroupSortDesc();
//        testFindFeedsByGroupSortRound();
            
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        
    }

    private void testFindFeedsSortRound() throws Exception {
        
        //Query Params
        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8"));
        queryParams.put("sort", "round");

        logger.info("Api Test Find Feeds sort round");

        Response response = restClient.get(AppConstants.FEED_PATH, queryParams);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);

        Assert.assertTrue(feeds.size() > 0);
        
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getPublished() + "]");
        }


    }

    private void testFindFeedsSortAsc() throws Exception {

        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8"));
        queryParams.put("sort", "asc");

        logger.info("Api Test Find Feeds sort asc");

        Response response = restClient.get(AppConstants.FEED_PATH, queryParams);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);
        
        Assert.assertTrue(feeds.size() > 0);
        
        FeedTestHelper.validateFeedSortedChronologicalAsc(feeds);


    }    
    
    private void testFindFeedsSortDesc() throws Exception {

        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8"));
        queryParams.put("sort", "desc");

        logger.info("Api Test Find Feeds sort desc");

        Response response = restClient.get(AppConstants.FEED_PATH, queryParams);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);
        
        Assert.assertTrue(feeds.size() > 0);
        
        FeedTestHelper.validateFeedSortedChronologicalDesc(feeds);


    }

    private void testFindFeeds() throws Exception {

        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8"));
        
        logger.info("Api Test Find Feeds");

        Response response = restClient.get(AppConstants.FEED_PATH, queryParams);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);
        
        Assert.assertTrue(feeds.size() > 0);
        
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getDescription() + "]");
        }
        
        
    }

    private void testFindFeedsInvalidSort() throws Exception {
        
        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("sort", "JUNK");

        logger.info("Api Test Find Feeds invalid sort");

        Response response = restClient.get(AppConstants.FEED_PATH, queryParams);

        Assert.assertEquals(400, response.getStatus());
    }
    
    
    
    private void testFindFeedsByGroup() throws Exception {
        
        //create group
        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");
        urls.add("http://metallica.tumblr.com/rss");

        Group model = new Group("Test Group 10", urls);

        //create item
        String url = restClient.BASE_URL + AppConstants.GROUP_PATH;
        Response responsePost = restClient.post(url, model);
        Assert.assertEquals(201, responsePost.getStatus());
        Group created = responsePost.readEntity(Group.class);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        
        //find feeds
        String itemUrl = AppConstants.FEED_PATH.concat("/").concat(created.getId().toString());
        Response response = restClient.get(itemUrl);
        Assert.assertEquals(200, response.getStatus());
        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);
        
        Assert.assertTrue(feeds.size() > 0);
        
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getDescription() + "]");
        }
        
    }
}
