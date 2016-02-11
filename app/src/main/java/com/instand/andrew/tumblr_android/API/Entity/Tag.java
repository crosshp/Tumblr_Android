package com.instand.andrew.tumblr_android.API.Entity;

import io.realm.RealmObject;

/**
 * Created by Andrew on 09.02.2016.
 */
public class Tag extends RealmObject {
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
