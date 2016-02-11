package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.Context;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 23.12.2015.
 */
public class Tumblr_API {
    private String token1 = null;
    private String token2 = null;
    private Context context = null;
    Blog orderBlog = null;

    public Tumblr_API(String token1, String token2, Context context) {
        this.token1 = token1;
        this.token2 = token2;
        this.context = context;
        initializeClient();
    }

    public Map<String, String> getBaseInfo() {
        Map<String, String> baseInfo = new HashMap<>();
        baseInfo.put("blogName", user.getName());
        baseInfo.put("avatar", orderBlog.avatar(64));
        baseInfo.put("followers", String.valueOf(orderBlog.getFollowersCount()));
        baseInfo.put("followings", String.valueOf(user.getFollowingCount()));
        baseInfo.put("posts", String.valueOf(orderBlog.getPostCount()));
        baseInfo.put("notes", String.valueOf(orderBlog.getLikeCount()));
        baseInfo.put("slug",orderBlog.getTitle());
        return baseInfo;
    }

    String consumer_key = Auth.consumer_key;
    String secret_key = Auth.secret_key;

    public JumblrClient getClient() {
        return client;
    }

    public void setClient(JumblrClient client) {
        this.client = client;
    }

    JumblrClient client = null;
    User user = null;


    public void initializeClient() {

        client = new JumblrClient(consumer_key, secret_key);
        token1 = "thVvLDO23IeLLfrLV1SZIMLVcU0Is3SL72599S6nzUABTwYwM4";
        token2 = "Ao7iY4TH3CERnFAqqQkx8lPyRoC43qXMTHIUYPe0qrNxnde3Nw";
        client.setToken(token1, token2);
        user = client.user();
        orderBlog = client.blogInfo(user.getName());
    }

    /* public List<Follower> getFollowers() {
         return followers_api.getFollowersByTumblrApi();
     }*/
    public String getName() {
        return user.getName();
    }
    public String getUserAvatar() {
        return client.blogAvatar(user.getName(), 64);
    }



   /* public List<Follower> getFollowing() {
        return followers_api.getFollowing();
    }*/

   /* public List<Follower> getNewFollowers() {
        return followers_api.getNewFollowers();
    }*/

  /* public List<Follower> getUnfollowFollowers() {
        return followers_api.getDeleteFollowers();
    }*/





    public void getRatingListByLikes() {
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
