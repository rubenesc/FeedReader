/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.services;

import com.feed.api.constants.Sort;
import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.services.FeedService;
import com.feed.test.helpers.FeedTestHelper;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
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
public class FeedServiceTest {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedServiceTest.class);
    @Autowired
    FeedService feedService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void cleanUp() throws Exception {
    }

    @Test
    public void tests() throws Exception {

        testFindFeed();
        testFindFeedsSortChronologicalDescending();
        testFindFeedsSortRoundRobin();
        
    }

    private void testFindFeedsSortRoundRobin() throws Exception {
        
        FeedOptions options = new FeedOptions();
        options.getUrls().add("http://tuneage.com/rss");
        options.getUrls().add("http://metallica.tumblr.com/rss");
        options.getUrls().add("http://blog.bandpage.com/feed/");
        options.setSort(Sort.ROUNDROBIN);
        
        logger.info("");
        logger.info("[Find Feeds sort round robin][" + options.getUrls().size() + "]["+ options.getSort()+"]");
        
        List<Feed> feeds = feedService.find(options);
        Assert.assertNotNull("Feeds should not be null", feeds);

        Feed previous = null;
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getLink() + "][" + feed.getPublished() + "]");

            previous = feed;
        }
        
        
    }

    private void testFindFeedsSortChronologicalDescending() throws Exception {

        FeedOptions options = new FeedOptions();
        options.getUrls().add("http://tuneage.com/rss");
        options.getUrls().add("http://metallica.tumblr.com/rss");
        options.getUrls().add("http://blog.bandpage.com/feed/");

        logger.info("");
        logger.info("[Find Feeds sort chronological descending][" + options.getUrls().size() + "]["+ options.getSort()+"]");

        List<Feed> feeds = feedService.find(options);
        Assert.assertNotNull("Feeds should not be null", feeds);
        
        FeedTestHelper.validateFeedSortedChronologicalDesc(feeds);

    }

    private void testFindFeed() throws Exception {

        String feedUrl = "http://tuneage.com/rss";

        logger.info("");
        logger.info("[Find Feed][" + feedUrl + "]");

        List<Feed> feeds = feedService.find(feedUrl);
        Assert.assertNotNull("Feeds should not be null", feeds);

        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "]");
        }
    }

}
