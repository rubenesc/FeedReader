/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.domain;

import java.util.List;
import java.util.ArrayList;
import com.feed.api.constants.Sort;
import com.feed.api.exceptions.ValidationException;
import org.springframework.util.StringUtils;

/**
 *
 * @author ruben
 */
public class FeedOptions {
    
    private List<String> urls;
    private Integer groupId;
    private Sort sort;

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
    
    public FeedOptions(Integer groupId, String sortValue) throws ValidationException{
        this.groupId = groupId;
        validateSort(sortValue);
    }
    
    public FeedOptions(List<String> urls, String sortValue) throws ValidationException{
        
        if (urls == null) {
            throw new ValidationException("please specify a feed url");
        }
        
        this.urls = urls;
        validateSort(sortValue);
        
    }
    
    private void validateSort(String sortValue) throws ValidationException {
        
        if (!StringUtils.isEmpty(sortValue)) {
            
            Sort sortEnum = Sort.getEnum(sortValue);
            
            if (sortEnum == null){
                throw new ValidationException("please specify a valid sort paramter");
            }
            
            this.sort = sortEnum;
        }
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    
}
