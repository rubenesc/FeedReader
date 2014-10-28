/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.domain;

import java.util.List;

/**
 *
 * @author ruben
 */
public class Group {
    
    private Integer id;
    private String name;
    private List<String> urls;

    public Group() {
    }

    public Group(String name, List<String> urls) {
        this.name = name;
        this.urls = urls;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", urls=" + urls + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 71 * hash + (this.urls != null ? this.urls.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.urls != other.urls && (this.urls == null || !this.urls.equals(other.urls))) {
            return false;
        }
        return true;
    }
    
}
