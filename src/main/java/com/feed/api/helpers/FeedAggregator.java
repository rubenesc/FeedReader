/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers;

import com.feed.api.constants.Sort;
import static com.feed.api.constants.Sort.asc;
import static com.feed.api.constants.Sort.desc;
import static com.feed.api.constants.Sort.round;
import com.feed.api.helpers.sorting.SortingStrategy;
import com.feed.api.domain.Feed;
import com.feed.api.domain.FeedOptions;
import com.feed.api.helpers.sorting.ChronologicalAsc;
import com.feed.api.helpers.sorting.ChronologicalDesc;
import com.feed.api.helpers.sorting.RoundRobin;
import com.feed.api.helpers.sorting.Unsorted;
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
    
    private List<Feed> sort(SortingStrategy strategy) {
        
        return strategy.sort(this.feeds);
    }
    
    
    /**
     *
     * Sort feeds based based based on a sorting strategy
     *
     * @param feedAggregator
     * @param options
     * @return
     */
    
    public List<Feed> sortResults(Sort sortOpt) {

        if (sortOpt == null){
            return this.sort(new Unsorted());
        }
        
        
        List<Feed> results;
        
        switch (sortOpt) {

            case asc:
                results = this.sort(new ChronologicalAsc());
                break;

            case desc:
                results = this.sort(new ChronologicalDesc());
                break;

            case round:
                results = this.sort(new RoundRobin());
                break;

            default:
                results = this.sort(new Unsorted()); //default
        }

        return results;
    }    
    
    
}
