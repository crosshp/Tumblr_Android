package com.instand.andrew.tumblr_android.API.BusinessLogic;

import com.instand.andrew.tumblr_android.API.Entity.Follower;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 23.12.2015.
 */
public class Tumblr_API {
    private String token1 = null;
    private String token2 = null;
    List<Blog> followingBlogs = null;

    public Tumblr_API(String token1, String token2) {
        this.token1 = token1;
        this.token2 = token2;
        initializeClient();
        initializeData();
    }

    String consumer_key = Auth.consumer_key;
    String secret_key = Auth.secret_key;
    JumblrClient client = null;

    public void initializeClient() {

        client = new JumblrClient(consumer_key, secret_key);
        token1 = "thVvLDO23IeLLfrLV1SZIMLVcU0Is3SL72599S6nzUABTwYwM4";
        token2 = "Ao7iY4TH3CERnFAqqQkx8lPyRoC43qXMTHIUYPe0qrNxnde3Nw";
        client.setToken(token1, token2);
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

    ;

    public Follower getFollowerByBlog(String blogName) {
        Blog blog = client.blogInfo(blogName);
        Follower follower = new Follower();
        follower.setName(blog.getName());
        follower.setAvatar(blog.avatar());
        follower.setTitle(blog.getTitle());
        follower.setId(-1);
        return follower;
    }


    public List<Follower> getFollowersByDB() {
        ArrayList<Follower> followers = new ArrayList<Follower>();
        return followers;
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

    public List<Follower> getFollowers() {
        ArrayList<Follower> followers = new ArrayList<Follower>();
        return followers;
    }

    public void getNewFollowers() {
    }

    ;

    public void getUnfollowFollowers() {
    }

    ;


    public void getRatingListByLikes() {
    }

    ;


    public void getRatingListByComments() {
    }

    ;

    public void getNumberOfLikes() {
    }

    ;


    public void getNumberOfComments() {
    }

    ;

    public void getUserWhoMostLikesList() {
    }

    ;

    public void getUserWhoMostCommentsList() {
    }

    ;


    public void getGhostFollowers() {
    }

    ;


    public void getUsersWhichClientMostLiked() {
    }

    ;


    public void getFollowerStatisticsById(int id) {
    }

    ;

    public void getPopularTags() {
    }


    public void getSaldoNumbersOfGhosts() {
    }

    ;


    public void getSaldoNumbersOfFollowers() {
    }

    ;


    public void getListFollowerWhichUserMostLiked() {
    }

    ;


    public void getListFollowerWhichUserMostComments() {
    }

    ;


}
