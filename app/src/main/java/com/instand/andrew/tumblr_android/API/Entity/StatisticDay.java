package com.instand.andrew.tumblr_android.API.Entity;

import io.realm.RealmObject;

/**
 * Created by Andrew on 10.02.2016.
 */
public class StatisticDay extends RealmObject {
    private Integer notesCount;
    private Integer postsCount;
    private Integer followersCount;
    private Integer followingsCount;
    private Integer dayOnTheYear;
    private Integer weekOnTheYear;
    private Integer month;
    private Integer year;

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

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
    }

    public Integer getDayOnTheYear() {
        return dayOnTheYear;
    }

    public void setDayOnTheYear(Integer dayOnTheYear) {
        this.dayOnTheYear = dayOnTheYear;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeekOnTheYear() {
        return weekOnTheYear;
    }

    public void setWeekOnTheYear(Integer weekOnTheYear) {
        this.weekOnTheYear = weekOnTheYear;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
