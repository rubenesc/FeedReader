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
public class Unsorted implements SortingStrategy {
    
    public List<Feed> sort(List<List<Feed>> feeds) {

        List<Feed> result = new ArrayList<Feed>();

        for (List<Feed> auxFeed : feeds) {
            result.addAll(auxFeed);
        }

        return result;
    }
    
    
}
