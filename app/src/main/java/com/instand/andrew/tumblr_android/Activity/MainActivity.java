package com.instand.andrew.tumblr_android.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.instand.andrew.tumblr_android.API.BusinessLogic.Inizialization;
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
    Spinner spinner = null;
    TextView followingCountTextView = null;
    TextView followerCountTextView = null;
    String DATE_PREFERENCE = "datePreference";
    ActivityDAO dao = null;
    DayStatisticDAO dayStatisticDAO = null;
    // String fontPath = "CenturyGothic.ttf";
    String fontPath = "Context Reprise ThinExp SSi Normal.ttf";
    TextView toolbarText = null;
    boolean isMontInitialize = false;

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

    TextView notesPersentTextView = null;
    ImageView notesImage = null;
    TextView notesLambdaTextView = null;

    TextView postsPersentTextView = null;
    ImageView postsImage = null;
    TextView postsLambdaTextView = null;

    TextView followerPersentTextView = null;
    ImageView followerImage = null;
    TextView followerLambdaTextView = null;

    TextView followingPersentTextView = null;
    ImageView followingImage = null;
    TextView followingLambdaTextView = null;

    ActivityEntity activityEntity = null;
    boolean isAvatarInitialize = false;
    Integer CURRENT_DAY = null;
    Integer CURRENT_MONTH = null;
    Integer CURRENT_YEAR = null;
    Integer CURRENT_WEEK = null;
    Inizialization inizialization = new Inizialization();


    public boolean isAvatarUpdated(String oldURL, String newURL) {
        return oldURL.equals(newURL);
    }

    public boolean isMonthInitialize() {
        boolean result = false;
        StatisticDay statisticDay = dayStatisticDAO.getDayStatisticByDay(CURRENT_DAY, CURRENT_YEAR);
        if (statisticDay != null) {
            Integer followersCount = statisticDay.getFollowersCount();
            Integer followingsCount = statisticDay.getFollowingsCount();
            Integer notesCount = statisticDay.getNotesCount();
            Integer postsCount = statisticDay.getPostsCount();
            monthFollowerValueTextView.setText(String.valueOf(followersCount));
            monthFollowingValueTextView.setText(String.valueOf(followingsCount));
            monthNotesValueTextView.setText(String.valueOf(notesCount));
            monthPostsValueTextView.setText(String.valueOf(postsCount));

            Integer lambdaNotes = notesCount - activityEntity.getNotesCount();
            Integer lambdaPosts = postsCount - activityEntity.getPostsCount();
            Integer lambdaFollowers = followersCount - activityEntity.getFollower();
            Integer lambdaFollowings = followingsCount - activityEntity.getFollowing();

            setGraphicComponent(notesPersentTextView, notesImage, notesLambdaTextView,
                    inizialization.getLambdaPersentCount(activityEntity.getNotesCount(), notesCount), lambdaNotes, (lambdaNotes >= 0));
            setGraphicComponent(postsPersentTextView, postsImage, postsLambdaTextView,
                    inizialization.getLambdaPersentCount(activityEntity.getPostsCount(), postsCount), lambdaPosts, (lambdaPosts >= 0));
            setGraphicComponent(followerPersentTextView, followerImage, followerLambdaTextView,
                    inizialization.getLambdaPersentCount(activityEntity.getFollower(), followersCount), lambdaFollowers, (lambdaFollowers >= 0));
            setGraphicComponent(followingPersentTextView, followingImage, followingLambdaTextView,
                    inizialization.getLambdaPersentCount(activityEntity.getFollowing(), followingsCount), lambdaFollowings, (lambdaFollowings >= 0));
            return true;
        }
        return result;
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
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateGraphicByPeriod(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSupportActionBar(toolbar);
        initializeFonts();

        dao = new ActivityDAO(this);
        dayStatisticDAO = new DayStatisticDAO(this);
        activityEntity = dao.getActivity();
        isAvatarInitialize = initializeAvatar();
        if (activityEntity != null) {
            initializeActivityCount(activityEntity);
            isMontInitialize = isMonthInitialize();
            spinner.setEnabled(true);
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

    public Integer getDay() {
        SharedPreferences prefs = getSharedPreferences(DATE_PREFERENCE, MODE_PRIVATE);
     //   return prefs.getInt("day", 0);
        return 5;
    }

    public Integer getYear() {
        SharedPreferences prefs = getSharedPreferences(DATE_PREFERENCE, MODE_PRIVATE);
        return prefs.getInt("year", 0);
    }

    public boolean isUpdate() {
        Calendar calendar = new GregorianCalendar();
        Integer day = calendar.get(Calendar.DAY_OF_YEAR);
        Integer year = calendar.get(Calendar.YEAR);
        return (day.equals(getDay()) && (year.equals(getYear())) && isMonthInitialize());
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
            spinner.setEnabled(false);
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
            spinner.setEnabled(true);
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


    public void updateGraphicByPeriod(Integer period) {
        progressBar.setVisibility(View.VISIBLE);
        ActivityEntity activityEntity = dao.getActivity();
        String periodString = "";
        switch (period) {
            case 0: {
                StatisticDay statisticDay = dayStatisticDAO.getDayStatisticByDay(CURRENT_DAY, CURRENT_YEAR);
                updateGraphic(statisticDay, activityEntity);
                periodString = "Day";
                monthFollowerTextView.setText(periodString);
                monthFollowingTextView.setText(periodString);
                monthPostsTextView.setText(periodString);
                monthNotesTextView.setText(periodString);
                break;
            }
            case 1: {
                StatisticDay statisticDay = dayStatisticDAO.getDayStatisticByWeek(CURRENT_WEEK, CURRENT_YEAR);
                updateGraphic(statisticDay, activityEntity);
                periodString = "Week";
                monthFollowerTextView.setText(periodString);
                monthFollowingTextView.setText(periodString);
                monthPostsTextView.setText(periodString);
                monthNotesTextView.setText(periodString);
                break;
            }
            case 2: {
                StatisticDay statisticDay = dayStatisticDAO.getDayStatisticByMonth(CURRENT_MONTH, CURRENT_YEAR);
                updateGraphic(statisticDay, activityEntity);
                periodString = "Month";
                monthFollowerTextView.setText(periodString);
                monthFollowingTextView.setText(periodString);
                monthPostsTextView.setText(periodString);
                monthNotesTextView.setText(periodString);
                break;
            }
            default:
                break;
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void updateGraphic(StatisticDay lessDay, ActivityEntity currentDay) {
        if ((lessDay != null) && (currentDay != null)) {
            Integer followersCount = currentDay.getFollower();
            Integer followingsCount = currentDay.getFollowing();
            Integer notesCount = currentDay.getNotesCount();
            Integer postsCount = currentDay.getPostsCount();
            monthFollowerValueTextView.setText(String.valueOf(lessDay.getFollowersCount()));
            monthFollowingValueTextView.setText(String.valueOf(lessDay.getFollowingsCount()));
            monthNotesValueTextView.setText(String.valueOf(lessDay.getNotesCount()));
            monthPostsValueTextView.setText(String.valueOf(lessDay.getPostsCount()));

            Integer lambdaNotes = notesCount - lessDay.getNotesCount();
            Integer lambdaPosts = postsCount - lessDay.getPostsCount();
            Integer lambdaFollowers = followersCount - lessDay.getFollowersCount();
            Integer lambdaFollowings = followingsCount - lessDay.getFollowingsCount();

            setGraphicComponent(notesPersentTextView, notesImage, notesLambdaTextView,
                    inizialization.getLambdaPersentCount(lessDay.getNotesCount(), notesCount), lambdaNotes, (lambdaNotes >= 0));
            setGraphicComponent(postsPersentTextView, postsImage, postsLambdaTextView,
                    inizialization.getLambdaPersentCount(lessDay.getPostsCount(), postsCount), lambdaPosts, (lambdaPosts >= 0));
            setGraphicComponent(followerPersentTextView, followerImage, followerLambdaTextView,
                    inizialization.getLambdaPersentCount(lessDay.getFollowersCount(), followersCount), lambdaFollowers, (lambdaFollowers >= 0));
            setGraphicComponent(followingPersentTextView, followingImage, followingLambdaTextView,
                    inizialization.getLambdaPersentCount(lessDay.getFollowingsCount(), followingsCount), lambdaFollowings, (lambdaFollowings >= 0));
        }
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

        notesPersentTextView = (TextView) findViewById(R.id.persentNotesCount);
        notesImage = (ImageView) findViewById(R.id.notesImage);
        notesLambdaTextView = (TextView) findViewById(R.id.lambdaNotesCount);

        postsPersentTextView = (TextView) findViewById(R.id.persentPostsCount);
        postsImage = (ImageView) findViewById(R.id.postsImage);
        postsLambdaTextView = (TextView) findViewById(R.id.lambdaPostsCount);

        followerPersentTextView = (TextView) findViewById(R.id.persentFollowerCount);
        followerImage = (ImageView) findViewById(R.id.followerImage);
        followerLambdaTextView = (TextView) findViewById(R.id.lambdaFollowersCount);

        followingPersentTextView = (TextView) findViewById(R.id.persentFollowingCount);
        followingImage = (ImageView) findViewById(R.id.followingImage);
        followingLambdaTextView = (TextView) findViewById(R.id.lambdaFollowingCount);


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

        notesPersentTextView.setTypeface(typeface);
        notesLambdaTextView.setTypeface(typeface);

        postsPersentTextView.setTypeface(typeface);
        postsLambdaTextView.setTypeface(typeface);

        followerPersentTextView.setTypeface(typeface);
        followerLambdaTextView.setTypeface(typeface);

        followingPersentTextView.setTypeface(typeface);
        followingLambdaTextView.setTypeface(typeface);

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
    }

    public void saveDate() {
        Calendar calendar = new GregorianCalendar();
        Integer day = calendar.get(Calendar.DAY_OF_YEAR);
        Integer year = calendar.get(Calendar.YEAR);
        SharedPreferences.Editor editor = getSharedPreferences(DATE_PREFERENCE, MODE_PRIVATE).edit();
        editor.putInt("day", day);
        editor.putInt("year", year);
        editor.commit();
    }

    public void setGraphicComponent(TextView persentTextView, ImageView imageView, TextView countTextView, String persent, Integer count, boolean isUp) {
        countTextView.setText(String.valueOf(count));
        persentTextView.setText(persent);
        if (isUp) {
            countTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            persentTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            imageView.setImageResource(R.mipmap.white_up_36dp);
        } else {
            countTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            persentTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            imageView.setImageResource(R.mipmap.white_down_36dp);
        }
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
