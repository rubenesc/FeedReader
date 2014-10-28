/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.helpers;

import com.feed.api.domain.Feed;
import com.feed.api.domain.Group;
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

    public static void verifyItemMatch(Group expected, Group actual) {
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertArrayEquals(expected.getUrls().toArray(), actual.getUrls().toArray());

        if (expected.getId() != null) {
            Assert.assertEquals(expected.getId(), actual.getId());
        }
        
    }

    public static void validateFeedSortedChronologicalAsc(List<Feed> feeds) {
        Feed previous = null;
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getPublished() + "]");

            if (previous != null) {
                boolean isAfter = previous.getPublished().after(feed.getPublished());
                Assert.assertFalse("Feed is not in chronological ascending order, [" + previous.getPublished() + "] shoud come before [" + feed.getPublished() + "]", isAfter);
            }

            previous = feed;
        }
    }

    public static void validateFeedSortedChronologicalDesc(List<Feed> feeds) {
        Feed previous = null;
        for (Iterator<Feed> it = feeds.iterator(); it.hasNext();) {
            Feed feed = it.next();
            logger.info("[" + feed.getTitle() + "][" + feed.getPublished() + "]");

            if (previous != null) {
                boolean isBefore = previous.getPublished().before(feed.getPublished());
                Assert.assertFalse("Feed is not in chronological descending order [" + previous.getPublished() + "] should come after [" + feed.getPublished() + "]", isBefore);
            }

            previous = feed;
        }
    }
}
