package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.ActivityEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Andrew on 10.02.2016.
 */
public class ActivityDAO {
    Context context = null;
    Realm realm = null;

    public ActivityDAO(Context context) {
        this.context = context;
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getInstance(config);
    }

    public void saveActivityInfo(ActivityEntity activity) {
        realm.beginTransaction();
        realm.clear(ActivityEntity.class);
        realm.copyToRealm(activity);
        realm.commitTransaction();
    }

    public ActivityEntity getActivity() {
        ActivityEntity activityEntity;
        try {
            activityEntity = realm.allObjects(ActivityEntity.class).last();
        } catch (Exception e) {
            return null;
        }
        return activityEntity;
    }
}
