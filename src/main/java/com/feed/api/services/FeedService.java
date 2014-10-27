/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.services;

import static com.feed.api.constants.Sort.desc;
import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.exceptions.ValidationException;
import com.feed.api.helpers.FeedAggregator;
import com.feed.api.helpers.FeedHelper;
import com.feed.api.helpers.sorting.ChronologicalAsc;
import com.feed.api.helpers.sorting.ChronologicalDesc;
import com.feed.api.helpers.sorting.RoundRobin;
import com.feed.api.helpers.sorting.Unsorted;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author ruben
 */
@Service
public class FeedService {

    private static Map<String, List<Feed>> cache = new HashMap<String, List<Feed>>();
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedService.class);

    public List<Feed> find(FeedOptions options) throws Exception {

        FeedAggregator feedAggregator = new FeedAggregator();

        for (String feedUrl : options.getUrls()) {


            List<Feed> feedResult = null;

            feedResult = cache.get(feedUrl);
            
            if (feedResult == null) {
                logger.info("searching for feed ["+feedUrl+"]");
                feedResult = this.find(feedUrl);
                cache.put(feedUrl, feedResult);
            }

            feedAggregator.add(feedResult);
        }

        return feedAggregator.sortResults(options.getSort());

    }

    public List<Feed> find(String feedUrl) throws Exception {

        URL url = null;
        try {
            url = new URL(feedUrl);
        } catch (MalformedURLException ex) {
            throw new ValidationException("Invalid feed url: " + feedUrl, ex);
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(url));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<Feed>();
        }

        return FeedHelper.convertFeedEntriesToModels(feed.getEntries());

    }

}
