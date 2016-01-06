package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.DataBase.DataBaseHelper;
import com.instand.andrew.tumblr_android.API.Entity.Post;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 06.01.2016.
 */
public class Posts_API {
    JumblrClient client = null;
    Context context = null;
    DataBaseHelper dataBase = new DataBaseHelper(context);
    User user = null;

    public Posts_API(JumblrClient client, Context context) {
        this.client = client;
        this.context = context;
        user = client.user();
    }

    public List<Post> getPostsByTumblrAPI() {
        List<com.tumblr.jumblr.types.Post> posts = client.blogPosts(user.getName());
        List<Post> resultList = new ArrayList<>();
        for (com.tumblr.jumblr.types.Post post : posts) {
            Post currentPost = new Post();
            currentPost.setBlogName(post.getBlogName());
            currentPost.setSlug(post.getSlug());
            currentPost.setTags(post.getTags());
            currentPost.setDate(post.getDateGMT());
            currentPost.setUrl(post.getPostUrl());
            resultList.add(currentPost);
        }
        return resultList;
    }

    public static String getLinkForLikes(String url) {
        return url.substring(0, url.lastIndexOf("/"));
    }
}
