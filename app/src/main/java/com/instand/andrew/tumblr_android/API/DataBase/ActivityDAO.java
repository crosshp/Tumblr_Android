package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.ActivityEntity;

import io.realm.Realm;

/**
 * Created by Andrew on 10.02.2016.
 */
public class ActivityDAO {
    Context context = null;

    public ActivityDAO(Context context) {
        this.context = context;
    }

    public void saveActivityInfo(ActivityEntity activity) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.clear(ActivityEntity.class);
        realm.copyToRealm(activity);
        realm.commitTransaction();
    }

    public ActivityEntity getActivity() {
        Realm realm = Realm.getInstance(context);
        ActivityEntity activityEntity = null;
        try {
            activityEntity = realm.allObjects(ActivityEntity.class).first();
        } catch (Exception e) {
        }
        return activityEntity;
    }
}
