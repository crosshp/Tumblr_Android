package com.instand.andrew.tumblr_android.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.instand.andrew.tumblr_android.API.BusinessLogic.Tumblr_API;
import com.instand.andrew.tumblr_android.R;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Activity activity = this;
    Toolbar toolbar = null;
    Tumblr_API tumblr_api = null;
    ProgressBar progressBar = null;
    TextView followingCountTextView = null;
    TextView followerCountTextView = null;
    String fontPath = "CenturyGothic.ttf";
    RecyclerView recyclerView = null;
    LinearLayoutManager llm = null;
    StaticCardAdapter staticCardAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_title);
        toolbarText.setText("andrew.kovtun");
        setSupportActionBar(toolbar);
        new InitializeTask().execute();

    }


    class NetworkTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            new DownloadImageTask((ImageView) findViewById(R.id.avatarImageView))
                    .execute(tumblr_api.getUserAvatar());
            return null;
        }
    }

    class InitializeTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            followingCountTextView = (TextView) findViewById(R.id.followingCountTextView);
            followerCountTextView = (TextView) findViewById(R.id.followerCountTextView);
            TextView followerText = (TextView) findViewById(R.id.followersText);
            TextView followingText = (TextView) findViewById(R.id.followingText);
            Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
            followingCountTextView.setTypeface(typeface);
            followerCountTextView.setTypeface(typeface);
            followerText.setTypeface(typeface);
            followingText.setTypeface(typeface);

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            followingCountTextView.setText(strings.get(1));
            followerCountTextView.setText(strings.get(0));
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(staticCardAdapter);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            tumblr_api = new Tumblr_API("", "", activity);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(tumblr_api.getFollowersCount()));
            arrayList.add(String.valueOf(tumblr_api.getFollowingCount()));
            staticCardAdapter = new StaticCardAdapter(tumblr_api.getStaticCards());
            new NetworkTask().execute();
            return arrayList;
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
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

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(getRoundedCornerBitmap(result, 64));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
