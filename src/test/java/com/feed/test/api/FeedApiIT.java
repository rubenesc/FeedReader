/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.api;

import com.feed.api.domain.Feed;
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
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
/**
 *
 * @author ruben
 */
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

        testFindFeeds();
        testFindFeedsSortAsc();
        testFindFeedsSortDesc();
        testFindFeedsSortRound();
        
        testFindFeedsInvalidSort();
    }

    private void testFindFeedsSortRound() throws Exception {
        
        //Query Params
        Multimap<String, String> queryParams = new ArrayListMultimap<String, String>();
        queryParams.put("url", URLEncoder.encode("http://tuneage.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8"));
        queryParams.put("url", URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8"));
        queryParams.put("sort", "round");

        String getUrl = restClient.FEED_API_URL;

        logger.info("Api Test Find Feeds sort round  [" + getUrl + "]");

        Response response = restClient.get(getUrl, queryParams);

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

        String getUrl = restClient.FEED_API_URL;

        logger.info("Api Test Find Feeds sort asc  [" + getUrl + "]");

        Response response = restClient.get(getUrl, queryParams);

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

        String getUrl = restClient.FEED_API_URL;

        logger.info("Api Test Find Feeds sort desc  [" + getUrl + "]");

        Response response = restClient.get(getUrl, queryParams);

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
        
        String getUrl = restClient.FEED_API_URL;

        logger.info("Api Test Find Feeds  [" + getUrl + "]");

        Response response = restClient.get(getUrl, queryParams);

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

        String getUrl = restClient.FEED_API_URL;

        logger.info("Api Test Find Feeds invalid sort [" + getUrl + "]");

        Response response = restClient.get(getUrl, queryParams);

        Assert.assertEquals(400, response.getStatus());
    }
}
