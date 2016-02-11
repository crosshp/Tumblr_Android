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

    private Integer notesCount;
    private Integer notesCountWeek;
    private Integer notesCountDay;

    private Integer postsCount;
    private Integer postsCountWeek;
    private Integer postsCountDay;

    private Integer followersCountWeek;
    private Integer followersCountDay;

    private Integer followingsCountWeek;
    private Integer followingsCountDay;

    public ActivityEntity() {
        follower = 0;
        following = 0;
        blogName = "Tumblr Statistics";
        avatarURL = null;
        notesCount = 0;
        notesCountWeek = 0;
        notesCountDay = 0;
        postsCount = 0;
        postsCountWeek = 0;
        postsCountDay = 0;
        followersCountWeek = 0;
        followersCountDay = 0;
        followingsCountWeek = 0;
        followingsCountDay = 0;
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

    public Integer getNotesCountWeek() {
        return notesCountWeek;
    }

    public void setNotesCountWeek(Integer notesCountWeek) {
        this.notesCountWeek = notesCountWeek;
    }

    public Integer getNotesCountDay() {
        return notesCountDay;
    }

    public void setNotesCountDay(Integer notesCountDay) {
        this.notesCountDay = notesCountDay;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getPostsCountWeek() {
        return postsCountWeek;
    }

    public void setPostsCountWeek(Integer postsCountWeek) {
        this.postsCountWeek = postsCountWeek;
    }

    public Integer getPostsCountDay() {
        return postsCountDay;
    }

    public void setPostsCountDay(Integer postsCountDay) {
        this.postsCountDay = postsCountDay;
    }

    public Integer getFollowersCountWeek() {
        return followersCountWeek;
    }

    public void setFollowersCountWeek(Integer followersCountWeek) {
        this.followersCountWeek = followersCountWeek;
    }

    public Integer getFollowersCountDay() {
        return followersCountDay;
    }

    public void setFollowersCountDay(Integer followersCountDay) {
        this.followersCountDay = followersCountDay;
    }

    public Integer getFollowingsCountWeek() {
        return followingsCountWeek;
    }

    public void setFollowingsCountWeek(Integer followingsCountWeek) {
        this.followingsCountWeek = followingsCountWeek;
    }

    public Integer getFollowingsCountDay() {
        return followingsCountDay;
    }

    public void setFollowingsCountDay(Integer followingsCountDay) {
        this.followingsCountDay = followingsCountDay;
    }
}
