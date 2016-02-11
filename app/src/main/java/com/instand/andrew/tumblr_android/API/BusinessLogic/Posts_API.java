package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.Post;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Posts_API {
    JumblrClient client = null;
    Context context = null;
    User user = null;

    public Posts_API(JumblrClient client, Context context) {
        this.client = client;
        this.context = context;
        user = client.user();
    }

    public List<com.tumblr.jumblr.types.Post> getPostsWithOffset(int offset) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", offset);
        List<com.tumblr.jumblr.types.Post> users = client.blogPosts("moan-s.tumblr.com", params);
        return users;
    }

    public String getUrlAvatar() {
        return client.blogAvatar(user.getName(), 64);
    }
}
