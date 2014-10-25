/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.domain;

import com.feed.api.constants.Sort;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruben
 */
public class FeedOptions {
    
    private List<String> urls;
    private Sort sort = Sort.DESC; //Default Value

    public FeedOptions() {
        urls = new ArrayList<String>();
    }
    
    public FeedOptions(List<String> urls) {
        this.urls = urls;
    }
    
    public FeedOptions(List<String> urls, Sort sort) {
        this.urls = urls;
        this.sort = sort;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
    
    
}
