package com.instand.andrew.tumblr_android.API.DataBase;

import com.instand.andrew.tumblr_android.API.Entity.Follower;

import java.util.List;

/**
 * Created by Andrew on 02.01.2016.
 */
public interface IDataBase {
    public void addFollower(Follower follower);

    public Follower getFollowerById(int id);

    public void deleteFollower(int id);

    public void updateFollower(Follower follower);

    public List<Follower> getAllFollower();
}
