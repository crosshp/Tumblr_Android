package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.Context;

import com.tumblr.jumblr.JumblrClient;

/**
 * Created by Andrew on 03.01.2016.
 */
public class Followers_API {
    JumblrClient client = null;

    public Followers_API(JumblrClient client, Context context) {
        this.client = client;
    }
}
