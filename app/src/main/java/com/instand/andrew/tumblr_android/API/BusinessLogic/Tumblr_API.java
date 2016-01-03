package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.Context;

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
    private Context context = null;
    Followers_API followers_api = null;

    public Tumblr_API(String token1, String token2, Context context) {
        this.token1 = token1;
        this.token2 = token2;
        this.context = context;
        initializeClient();
        followers_api = new Followers_API(client, context);

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

    public List<Follower> getFollowers() {
        return followers_api.getFollowersByTumblrApi();
    }

    public List<Follower> getFollowing() {
        return followers_api.getFollowing();
    }

    public void getNewFollowers() {
    }


    public void getUnfollowFollowers() {
    }


    public void getRatingListByLikes() {
    }


    public void getRatingListByComments() {
    }


    public void getNumberOfLikes() {
    }


    public void getUserWhoMostLikesList() {
    }


    public void getGhostFollowers() {
    }


    public void getUsersWhichClientMostLiked() {
    }


    public void getFollowerStatisticsById(int id) {
    }


    public void getPopularTags() {
    }


    public void getSaldoNumbersOfGhosts() {
    }


    public void getSaldoNumbersOfFollowers() {
    }


    public void getListFollowerWhichUserMostLiked() {
    }

}
