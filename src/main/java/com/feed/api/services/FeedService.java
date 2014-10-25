/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.services;

import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.exceptions.ValidationException;
import com.feed.api.helpers.FeedAggregator;
import com.feed.api.helpers.FeedHelper;
import com.feed.api.helpers.sorting.ChronologicalDesc;
import com.feed.api.helpers.sorting.RoundRobin;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FeedService.class);

    public List<Feed> find(FeedOptions options) throws Exception {

        FeedAggregator feedAggregator = new FeedAggregator();

        for (String feedUrl : options.getUrls()) {

            List<Feed> feedResult = this.find(feedUrl);
            feedAggregator.add(feedResult);
        }

        List<Feed> sort = sortResults(feedAggregator, options);

        return sort;

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

        List entries = feed.getEntries();

        List<Feed> feeds = FeedHelper.convertFeedEntriesToModels(entries);

        return feeds;

    }

    /**
     *
     * Sort feeds based based based on a sorting strategy
     *
     * @param feedAggregator
     * @param options
     * @return
     */
    private List<Feed> sortResults(FeedAggregator feedAggregator, FeedOptions options) {

        List<Feed> sort;

        switch (options.getSort()) {
            case DESC:
                sort = feedAggregator.sort(new ChronologicalDesc());
                break;

            case ROUNDROBIN:
                sort = feedAggregator.sort(new RoundRobin());
                break;

            default:
                sort = feedAggregator.sort(new ChronologicalDesc());
        }

        return sort;
    }
}
