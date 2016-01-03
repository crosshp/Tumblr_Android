package com.instand.andrew.tumblr_android.API.Entity;

/**
 * Created by Andrew on 31.12.2015.
 */
public class Follower {
    private String name;
    private String avatar;
    private String title;
    private boolean isFollow;
    private int id;
    private boolean isDelete;

    public Follower(String name, String title, String avatar, boolean isFollow, int id, boolean isDelete) {
        this.name = name;
        this.avatar = avatar;
        this.title = title;
        this.isFollow = isFollow;
        this.id = id;
        this.isDelete = isDelete;
    }

    public Follower() {
    }

    public Follower(String name, String title, String avatar, boolean isFollow, int id) {
        this.name = name;
        this.avatar = avatar;
        this.isFollow = isFollow;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String toString() {
        return "name = " + name + "\n title = " + title + "\n avatar = " + avatar + "\n isFollow = " + isFollow + "\n isDelete = " + isDelete;
    }


}
