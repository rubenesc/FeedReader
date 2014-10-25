/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.helpers;

import com.feed.api.domain.Feed;
import com.feed.test.services.FeedServiceTest;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ruben
 */
public class FeedTestHelper {
 
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedServiceTest.class);
    
    public static void validateFeedSortedChronologicalDesc(List<Feed> feeds) {
        Feed previous = null;
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getPublished() + "]");

            if (previous != null) {
                boolean isChro = previous.getPublished().before(feed.getPublished());
                Assert.assertFalse("Feed is not in chronological descending order [" + previous.getPublished() + "]<[" + feed.getPublished() + "]", isChro);
            }

            previous = feed;
        }
    }
    
    
}
