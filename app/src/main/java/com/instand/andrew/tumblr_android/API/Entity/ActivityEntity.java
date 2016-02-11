package com.instand.andrew.tumblr_android.API.Entity;

import io.realm.RealmObject;

/**
 * Created by Andrew on 10.02.2016.
 */
public class ActivityEntity extends RealmObject {
    private Integer follower;
    private Integer following;
    private String blogName;
    private String avatarURL;
    private String slugName;
    private Integer notesCount;
    private Integer postsCount;


    public ActivityEntity() {
        follower = 0;
        following = 0;
        blogName = "Tumblr Statistics";
        avatarURL = null;
        notesCount = 0;
        postsCount = 0;
        slugName = "";
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public Integer getNotesCount() {
        return notesCount;
    }

    public void setNotesCount(Integer notesCount) {
        this.notesCount = notesCount;
    }


    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }
}
