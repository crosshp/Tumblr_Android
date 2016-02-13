package com.instand.andrew.tumblr_android.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.instand.andrew.tumblr_android.API.BusinessLogic.Tumblr_API;
import com.instand.andrew.tumblr_android.API.DataBase.ActivityDAO;
import com.instand.andrew.tumblr_android.API.DataBase.DayStatisticDAO;
import com.instand.andrew.tumblr_android.API.Entity.ActivityEntity;
import com.instand.andrew.tumblr_android.API.Entity.StatisticDay;
import com.instand.andrew.tumblr_android.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Activity activity = this;
    Toolbar toolbar = null;
    Tumblr_API tumblr_api = null;
    ImageView avatarImageView = null;
    ProgressBar progressBar = null;
    TextView followingCountTextView = null;
    TextView followerCountTextView = null;
    String DATE_PREFERENCE = "datePreference";
    ActivityDAO dao = null;
    DayStatisticDAO dayStatisticDAO = null;
    // String fontPath = "CenturyGothic.ttf";
    String fontPath = "Context Reprise ThinExp SSi Normal.ttf";
    TextView toolbarText = null;


    TextView postsTextView = null;
    TextView postsValueTextView = null;
    TextView notesTextView = null;
    TextView notesValueTextView = null;
    TextView followerTextView = null;
    TextView followerValueTextView = null;
    TextView followingTextView = null;
    TextView followingValueTextView = null;
    TextView followerText = null;
    TextView followingText = null;
    TextView titleText = null;

    TextView monthPostsTextView = null;
    TextView monthPostsValueTextView = null;
    TextView monthNotesTextView = null;
    TextView monthNotesValueTextView = null;
    TextView monthFollowerTextView = null;
    TextView monthFollowerValueTextView = null;
    TextView monthFollowingTextView = null;
    TextView monthFollowingValueTextView = null;

    TextView upPersentCount = null;
    TextView upCount = null;
    TextView downPersentCount = null;
    TextView downCount = null;
    ActivityEntity activityEntity = null;
    boolean isAvatarInitialize = false;
    Integer CURRENT_DAY = null;
    Integer CURRENT_MONTH = null;
    Integer CURRENT_YEAR = null;
    Integer CURRENT_WEEK = null;


    public boolean isAvatarUpdated(String oldURL, String newURL) {
        return oldURL.equals(newURL);
    }

    public void testMonthYearDay() {
        dayStatisticDAO.deleteAllDayStatistic();
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 31; j++) {
                count += j;
                StatisticDay statisticDay = new StatisticDay();
                statisticDay.setNotesCount(count);
                statisticDay.setPostsCount(count);
                statisticDay.setFollowersCount(count);
                statisticDay.setFollowingsCount(count);
                statisticDay.setDayOnTheYear(CURRENT_DAY+j);
                statisticDay.setMonth(CURRENT_MONTH+i);
                statisticDay.setWeekOnTheYear(CURRENT_WEEK+i);
                statisticDay.setYear(CURRENT_YEAR);
                dayStatisticDAO.saveDayStatistic(statisticDay);
            }
        }
        for(StatisticDay statisticDay:dayStatisticDAO.getAllDayStatistic()){
            System.out.println(statisticDay);
        }
        System.out.println("Ololo");
        System.out.println(dayStatisticDAO.getDayStatisticByMonth(9,2016));
        System.out.println(dayStatisticDAO.getDayStatisticByDay(69,2016));
        System.out.println(dayStatisticDAO.getDayStatisticByWeek(13,2016));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCurrentDate();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarText = (TextView) findViewById(R.id.toolbar_title);
        avatarImageView = (ImageView) findViewById(R.id.avatarImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        initializeFonts();

        dao = new ActivityDAO(this);
        dayStatisticDAO = new DayStatisticDAO(this);
        testMonthYearDay();
        activityEntity = dao.getActivity();
        isAvatarInitialize = initializeAvatar();
        if (activityEntity != null) {
            initializeActivityCount(activityEntity);
        }
        if (!hasConnection(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            if (!isUpdate()) {
                progressBar.setVisibility(View.VISIBLE);
                saveDate();
                new UpdateActivityInfoTask().execute();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean initializeAvatar() {
        System.out.println("NOT  DOWNLOAD!!!");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TumblrStat";
        File file = new File(path + "/orderAvatar" + ".png");
        try (FileInputStream fis = new FileInputStream(file)) {
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            avatarImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Integer getDate() {
        SharedPreferences prefs = getSharedPreferences(DATE_PREFERENCE, MODE_PRIVATE);
        return prefs.getInt("day", 0);
    }

    public boolean isUpdate() {
        Calendar calendar = new GregorianCalendar();
        Integer day = calendar.get(Calendar.DAY_OF_YEAR);
        return day.equals(getDate());
    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        String name = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                name = urls[1];
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }

        public void savePicture(Bitmap bitmap, String name) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TumblrStat";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, name + ".png");
            try (FileOutputStream fOut = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(Bitmap result) {
            Bitmap resultBitMap = getRoundedCornerBitmap(result, 64);
            bmImage.setImageBitmap(resultBitMap);
            savePicture(resultBitMap, name);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class CheckImageInitializationTask extends AsyncTask<Void, Void, Boolean> {
        private String oldURL;
        String newURL = null;

        public CheckImageInitializationTask(String oldURL) {
            if (oldURL != null) {
                this.oldURL = oldURL;
            } else this.oldURL = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            newURL = tumblr_api.getUserAvatar();
            if (!isAvatarInitialize || !isAvatarUpdated(oldURL, newURL)) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                new DownloadImageTask(avatarImageView).execute(newURL, "orderAvatar");
            }
        }
    }

    private class UpdateActivityInfoTask extends AsyncTask<Void, Void, ActivityEntity> {
        @Override
        protected ActivityEntity doInBackground(Void... params) {
            tumblr_api = new Tumblr_API("", "", activity);
            Map<String, String> map = tumblr_api.getBaseInfo();
            ActivityEntity activityEntity = new ActivityEntity();
            activityEntity.setBlogName(map.get("blogName"));
            activityEntity.setAvatarURL(map.get("avatar"));
            activityEntity.setFollower(Integer.valueOf(map.get("followers")));
            activityEntity.setFollowing(Integer.valueOf(map.get("followings")));
            activityEntity.setPostsCount(Integer.valueOf(map.get("posts")));
            activityEntity.setNotesCount(Integer.valueOf(map.get("notes")));
            activityEntity.setSlugName(map.get("slug"));
            return activityEntity;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ActivityEntity activityEntity) {
            followerCountTextView.setText(String.valueOf(activityEntity.getFollower()));
            followingCountTextView.setText(String.valueOf(activityEntity.getFollowing()));
            notesValueTextView.setText(String.valueOf(activityEntity.getNotesCount()));
            postsValueTextView.setText(String.valueOf(activityEntity.getPostsCount()));
            toolbarText.setText(activityEntity.getBlogName());
            followerValueTextView.setText(String.valueOf(activityEntity.getFollower()));
            followingValueTextView.setText(String.valueOf(activityEntity.getFollowing()));
            titleText.setText(activityEntity.getSlugName());
            dao.saveActivityInfo(activityEntity);
            dayStatisticDAO.saveDayStatistic(convertActivityEntity(activityEntity));
            try {
                new CheckImageInitializationTask(activityEntity.getAvatarURL()).execute();
            } catch (NullPointerException e) {
                new CheckImageInitializationTask("").execute();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public StatisticDay convertActivityEntity(ActivityEntity activityEntity) {
        StatisticDay statisticDay = new StatisticDay();
        statisticDay.setNotesCount(activityEntity.getNotesCount());
        statisticDay.setPostsCount(activityEntity.getPostsCount());
        statisticDay.setFollowersCount(activityEntity.getFollower());
        statisticDay.setFollowingsCount(activityEntity.getFollowing());
        statisticDay.setDayOnTheYear(CURRENT_DAY);
        statisticDay.setMonth(CURRENT_MONTH);
        statisticDay.setWeekOnTheYear(CURRENT_WEEK);
        statisticDay.setYear(CURRENT_YEAR);
        return statisticDay;
    }

    public void initializeCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        CURRENT_DAY = calendar.get(Calendar.DAY_OF_YEAR);
        CURRENT_MONTH = calendar.get(Calendar.MONTH);
        CURRENT_YEAR = calendar.get(Calendar.YEAR);
        CURRENT_WEEK = calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public void initializeFonts() {
        postsTextView = (TextView) findViewById(R.id.postsTextView);
        postsValueTextView = (TextView) findViewById(R.id.valuePostsTextView);
        notesTextView = (TextView) findViewById(R.id.notesTextView);
        notesValueTextView = (TextView) findViewById(R.id.valueNotesTextView);
        followerTextView = (TextView) findViewById(R.id.followersTextView);
        followerValueTextView = (TextView) findViewById(R.id.valueFollowersTextView);
        followingTextView = (TextView) findViewById(R.id.followingTextView);
        followingValueTextView = (TextView) findViewById(R.id.valueFollowingTextView);
        titleText = (TextView) findViewById(R.id.slugName);

        monthPostsTextView = (TextView) findViewById(R.id.mounthPostsTextView);
        monthPostsValueTextView = (TextView) findViewById(R.id.mounthPostsValueTextView);
        monthNotesTextView = (TextView) findViewById(R.id.mounthNotesTextView);
        monthNotesValueTextView = (TextView) findViewById(R.id.mounthNotesValueTextView);
        monthFollowerTextView = (TextView) findViewById(R.id.mounthFollowersTextView);
        monthFollowerValueTextView = (TextView) findViewById(R.id.mounthFollowersValueTextView);
        monthFollowingTextView = (TextView) findViewById(R.id.mounthFollowingTextView);
        monthFollowingValueTextView = (TextView) findViewById(R.id.mounthFollowingValueTextView);

        followingCountTextView = (TextView) findViewById(R.id.followingCountTextView);
        followerCountTextView = (TextView) findViewById(R.id.followerCountTextView);
        followerText = (TextView) findViewById(R.id.followersText);
        followingText = (TextView) findViewById(R.id.followingText);

        upPersentCount = (TextView) findViewById(R.id.persentUpCount);
        upCount = (TextView) findViewById(R.id.lambdaUpCount);
        downPersentCount = (TextView) findViewById(R.id.persentDownCount);
        downCount = (TextView) findViewById(R.id.lambdaDownCount);

        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
        followingCountTextView.setTypeface(typeface);
        followerCountTextView.setTypeface(typeface);
        followerText.setTypeface(typeface);
        followingText.setTypeface(typeface);
        postsTextView.setTypeface(typeface);
        postsValueTextView.setTypeface(typeface);
        notesTextView.setTypeface(typeface);
        notesValueTextView.setTypeface(typeface);
        followerTextView.setTypeface(typeface);
        followerValueTextView.setTypeface(typeface);
        followingTextView.setTypeface(typeface);
        followingValueTextView.setTypeface(typeface);
        followerText.setTypeface(typeface);
        followingText.setTypeface(typeface);
        monthPostsTextView.setTypeface(typeface);
        monthPostsValueTextView.setTypeface(typeface);
        monthNotesTextView.setTypeface(typeface);
        monthNotesValueTextView.setTypeface(typeface);
        monthFollowerTextView.setTypeface(typeface);
        monthFollowerValueTextView.setTypeface(typeface);
        monthFollowingTextView.setTypeface(typeface);
        monthFollowingValueTextView.setTypeface(typeface);
        upPersentCount.setTypeface(typeface);
        upCount.setTypeface(typeface);
        downPersentCount.setTypeface(typeface);
        downCount.setTypeface(typeface);
        toolbarText.setTypeface(typeface);
        titleText.setTypeface(typeface);
    }

    public void initializeActivityCount(ActivityEntity activityEntity) {
        toolbarText.setText(String.valueOf(activityEntity.getBlogName()));
        postsValueTextView.setText(String.valueOf(activityEntity.getPostsCount()));
        notesValueTextView.setText(String.valueOf(activityEntity.getNotesCount()));
        followerValueTextView.setText(String.valueOf(activityEntity.getFollower()));
        followingValueTextView.setText(String.valueOf(activityEntity.getFollowing()));
        followerCountTextView.setText(String.valueOf(activityEntity.getFollower()));
        followingCountTextView.setText(String.valueOf(activityEntity.getFollowing()));
        titleText.setText(activityEntity.getSlugName());
        progressBar.setVisibility(View.INVISIBLE);
     /*   upPersentCount.setText(inizialization.getLambdaPersentCount());
        upCount.setText();
        downPersentCount.setText();
        downCount.setText();*/

    }

    public void saveDate() {
        Calendar calendar = new GregorianCalendar();
        Integer day = calendar.get(Calendar.DAY_OF_YEAR);
        SharedPreferences.Editor editor = getSharedPreferences(DATE_PREFERENCE, MODE_PRIVATE).edit();
        editor.putInt("day", day);
        editor.commit();
    }

  /*  public void getAllFollowers() {
        JumblrClient client = tumblr_api.getClient();
        int countOfPosts = client.blogInfo("moan-s.tumblr.com").getPostCount();
        int currentOffset = 0;
        do {
            new getAllPostsTask().execute(currentOffset);
            currentOffset += 20;
        } while (currentOffset < countOfPosts);
     /*   int maxCountOfThreads = Math.round(countOfPosts / 20);
        do {
            if (maxCountOfThreads < 10) {
                int endPart = Math.round(countOfPosts / maxCountOfThreads);
                for (int i = 0; i < maxCountOfThreads; i++) {
                    startPartOfOffset(endPart * i, (i + 1) * endPart);
                }
            } else {
                int endPart = Math.round(countOfPosts / 10);
                for (int i = 0; i < 10; i++) {
                    startPartOfOffset(endPart * i, (i + 1) * endPart);
                }
            }

        } while (currentOffset < countOfPosts || currentOffset < 10000);

    }*/
}
