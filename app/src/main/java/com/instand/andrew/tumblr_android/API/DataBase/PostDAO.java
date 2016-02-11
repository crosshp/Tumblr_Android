package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.Post;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Andrew on 09.02.2016.
 */
public class PostDAO {
    Context context = null;

    public PostDAO(Context context) {
        this.context = context;
    }

    public void savePosts(RealmList<Post> posts) {
        RealmDAO realmDAO = new RealmDAO();
        realmDAO.setContext(context);
        Realm realm = realmDAO.getRealm();
        realm.beginTransaction();
        realm.copyToRealm(posts);
        realm.commitTransaction();
    }

    public RealmResults<Post> getAllPosts() {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        RealmResults<Post> posts = realm.allObjects(Post.class);
        realm.commitTransaction();
        return posts;
    }
}
