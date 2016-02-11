package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Andrew on 10.02.2016.
 */
public class RealmDAO {

    public void setContext(Context context) {
        this.context = context;
    }

    Context context = null;

    public Realm getRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
        return Realm.getInstance(config);
    }
}
