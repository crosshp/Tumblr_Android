package com.instand.andrew.tumblr_android.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.instand.andrew.tumblr_android.API.BusinessLogic.Posts_API;
import com.instand.andrew.tumblr_android.API.BusinessLogic.Tumblr_API;
import com.instand.andrew.tumblr_android.API.DataBase.DataBaseHelper;
import com.instand.andrew.tumblr_android.API.Entity.Follower;
import com.instand.andrew.tumblr_android.API.Entity.Post;
import com.instand.andrew.tumblr_android.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NetworkTask().execute();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
       /* Follower follower = new Follower("ololo1", "titleOlolo1", "http1", false, -1,true);
        Follower follower1 = new Follower("ololo2", "titleOlolo2", "http2", false, -1,false);
        dataBaseHelper.addFollower(follower);
        dataBaseHelper.addFollower(follower1);
        //Follower follower = dataBaseHelper.getFollowerById(3);
        //System.out.println(follower.toString());
        List<Follower> followers = dataBaseHelper.getFollowerWithOutDeleteUser();
        for (Follower followr : followers) {
            System.out.println(followr.toString());
            System.out.println();
        }*/
    }


    class NetworkTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Tumblr_API api = new Tumblr_API("", "", activity);
            List<Post> posts = api.getPostsByTumblrAPI();
            for (Post post : posts) {
                System.out.println(post.toString());
                System.out.println(Posts_API.getLinkForLikes(post.getUrl()));
                System.out.println();
            }
            return null;
        }
    }
}
