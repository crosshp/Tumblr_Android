package com.instand.andrew.tumblr_android.API.BusinessLogic;

import com.instand.andrew.tumblr_android.API.Entity.Follower;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 03.01.2016.
 */
public class Followers_API {
    JumblrClient client = null;
    List<Blog> followingBlogs = null;

    public Followers_API(JumblrClient client) {
        this.client = client;
        initializeData();
    }

    public void initializeData() {
        followingBlogs = client.userFollowing();
    }

    public List<Follower> getFollowersByTumblrApi() {
        List<User> users = client.blogFollowers(client.user().getName());
        ArrayList<Follower> followers = new ArrayList<Follower>();
        Follower follower = null;
        for (User user : users) {
            follower = getFollowerByBlog(user.getName());
            follower.setIsFollow(user.isFollowing());
            followers.add(follower);
        }
        return followers;
    }

    public Follower getFollowerByBlog(String blogName) {
        Blog blog = client.blogInfo(blogName);
        Follower follower = new Follower();
        follower.setName(blog.getName());
        follower.setAvatar(blog.avatar());
        follower.setTitle(blog.getTitle());
        follower.setId(-1);
        return follower;
    }

    public List<Follower> getFollowing() {
        ArrayList<Follower> followers = new ArrayList<Follower>();
        for (Blog blog : followingBlogs) {
            Follower follower = new Follower();
            follower.setName(blog.getName());
            follower.setAvatar(blog.avatar());
            follower.setTitle(blog.getTitle());
            follower.setIsFollow(true);
            follower.setId(-1);
            followers.add(follower);
        }
        return followers;
    }

    public boolean equalNameFollower(List<Follower> followers, String name) {
        for (Follower follower : followers) {
            if (follower.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
