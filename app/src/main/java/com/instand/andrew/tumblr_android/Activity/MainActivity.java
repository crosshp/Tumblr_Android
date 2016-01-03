package com.instand.andrew.tumblr_android.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.instand.andrew.tumblr_android.API.BusinessLogic.Tumblr_API;
import com.instand.andrew.tumblr_android.API.Entity.Follower;
import com.instand.andrew.tumblr_android.R;

import java.util.List;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NetworkTask().execute();
    }
    public void main() {
        Tumblr_API api = new Tumblr_API("","");
        List<Follower> followers = api.getFollowing();
        for (Follower follower : followers) {
            System.out.println(follower.toString());
            System.out.println();
        }
    }


    class NetworkTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Tumblr_API api = new Tumblr_API("","");
            List<Follower> followers = api.getFollowers();
            for (Follower follower : followers) {
                System.out.println(follower.toString());
            }
            return null;
        }
    }
}
