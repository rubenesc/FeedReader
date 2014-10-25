/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.api;

import com.feed.api.domain.Feed;
import com.feed.test.api.ext.RestClient;
import com.feed.test.helpers.FeedTestHelper;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

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
    }

    @Test
    public void tests() throws Exception {

        testFindFeeds();
        testFindFeedsSortDesc();
        testFindFeedsSortRound();

    }
    private void testFindFeedsSortRound() throws Exception {
        
        String url1 = URLEncoder.encode("http://tuneage.com/rss", "UTF-8");
        String url2 = URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8");
        String url3 = URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8");
        
        String getUrl = restClient.FEED_API_URL + "?url=" + url1 + "&url=" + url2 + "&url=" + url3+"&sort=round";
        
        logger.info("Api Test Find Feeds sort round  [" + getUrl + "]");

        Response response = restClient.get(getUrl);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);

        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getPublished() + "]");
        }
        
    
    }
    
    
    private void testFindFeedsSortDesc() throws Exception {
        
        String url1 = URLEncoder.encode("http://tuneage.com/rss", "UTF-8");
        String url2 = URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8");
        String url3 = URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8");
        
        String getUrl = restClient.FEED_API_URL + "?url=" + url1 + "&url=" + url2 + "&url=" + url3+"&sort=desc";
        
        logger.info("Api Test Find Feeds sort desc  [" + getUrl + "]");

        Response response = restClient.get(getUrl);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);

        FeedTestHelper.validateFeedSortedChronologicalDesc(feeds);
        
    
    }

    private void testFindFeeds() throws Exception {
        
        String url1 = URLEncoder.encode("http://tuneage.com/rss", "UTF-8");
        String url2 = URLEncoder.encode("http://metallica.tumblr.com/rss", "UTF-8");
        String url3 = URLEncoder.encode("http://blog.bandpage.com/feed/", "UTF-8");
        
        String getUrl = restClient.FEED_API_URL + "?url=" + url1 + "&url=" + url2 + "&url=" + url3;
        
        logger.info("Api Test Find Feeds  [" + getUrl + "]");

        Response response = restClient.get(getUrl);

        Assert.assertEquals(200, response.getStatus());

        List<Feed> feeds = response.readEntity(new GenericType<List<Feed>>() {
        });
        Assert.assertNotNull("Feeds should not be null", feeds);

        FeedTestHelper.validateFeedSortedChronologicalDesc(feeds);

    }
}
