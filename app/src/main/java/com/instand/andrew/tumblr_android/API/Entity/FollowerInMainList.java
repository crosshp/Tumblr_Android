package com.instand.andrew.tumblr_android.API.Entity;

/**
 * Created by Andrew on 03.01.2016.
 */
public class FollowerInMainList {
    private Follower follower;
    private boolean isFollower;
    private boolean isFollowing;
    private boolean isDelete;

    public FollowerInMainList() {
    }

    public FollowerInMainList(Follower follower, boolean isFollower, boolean isFollowing, boolean isDelete) {
        this.follower = follower;
        this.isFollower = isFollower;
        this.isFollowing = isFollowing;
        this.isDelete = isDelete;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setIsFollower(boolean isFollower) {
        this.isFollower = isFollower;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
