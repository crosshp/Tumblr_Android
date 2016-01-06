package com.instand.andrew.tumblr_android.API.Entity;

import java.util.List;

/**
 * Created by Andrew on 06.01.2016.
 */
public class Post {
    private String blogName;
    private String slug;
    private String date;
    private List<String> tags;
    private String url;

    public Post() {
    }

    public Post(String blogName, String slug, String date, List<String> tags, String url) {
        this.blogName = blogName;
        this.slug = slug;
        this.date = date;
        this.tags = tags;
        this.url = url;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        String tags = "";
        if (this.tags.size() != 0) {
            for (String tag : this.tags) {
                tags += tag + ",";
            }
            tags = tags.substring(0, tags.length() - 1);
        }
        return "blogName =" + blogName + "\nslug = " + slug + "\ntags = " + tags + "\nurl = "+url+"\ndate = " + date + "\n";
    }
}
