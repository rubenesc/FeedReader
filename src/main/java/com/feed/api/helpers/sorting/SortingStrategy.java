/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.helpers.sorting;

import com.feed.api.domain.Feed;
import java.util.List;

/**
 *
 * @author ruben
 */
public interface SortingStrategy {

    public List<Feed> sort(List<List<Feed>> feeds);
}