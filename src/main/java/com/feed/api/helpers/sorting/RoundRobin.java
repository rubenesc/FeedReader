/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers.sorting;

import com.feed.api.domain.Feed;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruben
 */
public class RoundRobin implements SortingStrategy {

    public List<Feed> sort(List<List<Feed>> feeds) {

        List<Feed> result = new ArrayList<Feed>();

        int maxSize = findMaxFeedSize(feeds);

        //build feed
        for (int i = 0; i < maxSize; i++) {

            for (List<Feed> feed : feeds) {

                if (feed.size() > i) {
                    result.add(feed.get(i));
                }

            }
        }
        
        return result;
    }

    /**
     * Find the size of the longest feed in the list.
     * 
     * @param feeds
     * @return 
     */
    private int findMaxFeedSize(List<List<Feed>> feeds) {
        int maxSize = 0;
        for (List<Feed> feed : feeds) {
            if (feed.size() > maxSize) {
                maxSize = feed.size();
            }
        }
        return maxSize;
    }
}