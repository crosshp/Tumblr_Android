package com.instand.andrew.tumblr_android.API.Entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Andrew on 06.01.2016.
 */
public class Post extends RealmObject {
    private String blogName;
    private String slug;
    private String date;
    private RealmList<Tag> tags;
    private String url;

    public Post() {
    }

    public Post(String blogName, String slug, String date, RealmList<Tag> tags, String url) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

    /*public String toString() {
        String tags = "";
        if (this.tags.size() != 0) {
            for (String tag : this.tags) {
                tags += tag + ",";
            }
            tags = tags.substring(0, tags.length() - 1);
        }
        return "blogName =" + blogName + "\nslug = " + slug + "\ntags = " + tags + "\nurl = "+url+"\ndate = " + date + "\n";
    }*/
}
