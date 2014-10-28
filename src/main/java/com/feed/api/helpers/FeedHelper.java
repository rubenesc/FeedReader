/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers;

import com.feed.api.domain.Feed;
import com.sun.syndication.feed.synd.SyndEntry;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ruben
 */
public class FeedHelper {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedHelper.class);

    public static List<Feed> convertFeedEntriesToModels(List<SyndEntry> syndEntries) {

        List<Feed> response = new ArrayList<Feed>();

        for (SyndEntry entry : syndEntries) {
            response.add(new Feed(entry));
        }

        return response;
    }
}
