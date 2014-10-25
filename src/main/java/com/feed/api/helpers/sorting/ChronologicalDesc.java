/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers.sorting;

import com.feed.api.domain.Feed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author ruben
 */
public class ChronologicalDesc implements SortingStrategy {

    Comparator<Feed> comparator = new Comparator<Feed>() {
        
        public int compare(Feed m1, Feed m2) {
            return m2.getPublished().compareTo(m1.getPublished());
        }
        
    };

    public List<Feed> sort(List<List<Feed>> feeds) {

        List<Feed> result = new ArrayList<Feed>();

        for (List<Feed> auxFeed : feeds) {
            result.addAll(auxFeed);
        }

        Collections.sort(result, comparator);

        return result;
    }
}