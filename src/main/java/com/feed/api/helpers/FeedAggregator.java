/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers;

import com.feed.api.helpers.sorting.SortingStrategy;
import com.feed.api.domain.Feed;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruben
 */
public class FeedAggregator {
    
    List<List<Feed>> feeds;    

    public FeedAggregator() {
        this.feeds = new ArrayList<List<Feed>>();
    }

    public void add(List<Feed> feed) {
        this.feeds.add(feed);
    }
    
    public List<Feed> sort(SortingStrategy strategy) {
        
        return strategy.sort(this.feeds);
    }
    
    
}
