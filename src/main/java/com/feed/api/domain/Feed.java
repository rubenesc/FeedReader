/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.domain;

import com.sun.syndication.feed.synd.SyndEntry;
import java.util.Date;

/**
 *
 * @author ruben
 */
public class Feed {

    private String title;
    private String link;
    private String author;
    private String description;
    private Date published;

    public Feed() {
    }

    public Feed(SyndEntry entity) {

        this.title = entity.getTitle();
        this.link = entity.getLink();
        this.author = entity.getAuthor();
        if (entity.getDescription() != null) {
            this.description = entity.getDescription().getValue();
        }
        this.published = entity.getPublishedDate();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "Feed{" + "title=[" + title + "], link=[" + link + "], author=[" + author + "], description=[" + description + "], published=[" + published + "]}";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 37 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 37 * hash + (this.author != null ? this.author.hashCode() : 0);
        hash = 37 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 37 * hash + (this.published != null ? this.published.hashCode() : 0);
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
        final Feed other = (Feed) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.link == null) ? (other.link != null) : !this.link.equals(other.link)) {
            return false;
        }
        if ((this.author == null) ? (other.author != null) : !this.author.equals(other.author)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.published != other.published && (this.published == null || !this.published.equals(other.published))) {
            return false;
        }
        return true;
    }
}
