package com.instand.andrew.tumblr_android.View;

import android.app.Activity;
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
        Runnable myRunnable = new Runnable(){
            public void run(){
                System.out.println("Start!!!");
                System.out.println("Start!!!");
                System.out.println("Start!!!");
                System.out.println("Start!!!");
                System.out.println("Start!!!");
                Tumblr_API api = new Tumblr_API("","");
                List<Follower> followers = api.getFollowersByTumblrApi();
                for (Follower follower : followers) {
                    System.out.println(follower.toString());
                }
            }
        };

        Thread thread = new Thread(myRunnable);
        thread.start();
    }
    public void main() {
        Tumblr_API api = new Tumblr_API("","");
        List<Follower> followers = api.getFollowersByTumblrApi();
        for (Follower follower : followers) {
            System.out.println(follower.toString());
        }
    }

}
