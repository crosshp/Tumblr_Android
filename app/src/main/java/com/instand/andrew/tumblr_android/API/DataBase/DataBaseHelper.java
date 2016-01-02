package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.instand.andrew.tumblr_android.API.Entity.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 02.01.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper implements IDataBase {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tumblr";
    private static final String TABLE_FOLLOWER = "follower";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AVATAR = "avatar";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ISFOLLOW = "isFollow";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOLLOWER_TABLE = "CREATE TABLE " + TABLE_FOLLOWER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_TITLE + " TEXT," + COLUMN_AVATAR + " TEXT," + COLUMN_ISFOLLOW + " INTEGER" + ")";
        db.execSQL(CREATE_FOLLOWER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void addFollower(Follower follower) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, follower.getName());
        values.put(COLUMN_AVATAR, follower.getAvatar());
        values.put(COLUMN_TITLE, follower.getTitle());
        if (follower.isFollow()) {
            values.put(COLUMN_ISFOLLOW, 1);
        } else {
            values.put(COLUMN_ISFOLLOW, 0);
        }
        db.insert(TABLE_FOLLOWER, null, values);
        db.close();
    }

    @Override
    public Follower getFollowerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Follower follower = null;
        Cursor cursor = db.query(TABLE_FOLLOWER, new String[]{
                        COLUMN_NAME, COLUMN_TITLE, COLUMN_AVATAR, COLUMN_ISFOLLOW, COLUMN_ID}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            follower = new Follower();
            follower.setName(cursor.getString(0));
            follower.setTitle(cursor.getString(1));
            follower.setAvatar(cursor.getString(2));
            if (cursor.getInt(3) == 1) {
                follower.setIsFollow(true);
            } else {
                follower.setIsFollow(false);
            }
            follower.setId(cursor.getInt(4));
        }
        return follower;
    }

    @Override
    public void deleteFollower(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOLLOWER, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void updateFollower(Follower follower) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, follower.getName());
        values.put(COLUMN_AVATAR, follower.getAvatar());
        values.put(COLUMN_TITLE, follower.getTitle());
        if (follower.isFollow()) {
            values.put(COLUMN_ISFOLLOW, 1);
        } else {
            values.put(COLUMN_ISFOLLOW, 0);
        }
        db.update(TABLE_FOLLOWER, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(follower.getId())});
    }

    @Override
    public List<Follower> getAllFollower() {
        List<Follower> followers = new ArrayList<Follower>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOLLOWER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Follower follower = new Follower();
                follower.setName(cursor.getString(0));
                follower.setTitle(cursor.getString(1));
                follower.setAvatar(cursor.getString(2));
                if (cursor.getInt(3) == 1) {
                    follower.setIsFollow(true);
                } else {
                    follower.setIsFollow(false);
                }
                follower.setId(cursor.getInt(4));
                followers.add(follower);
            } while (cursor.moveToNext());
        }
        return followers;
    }
}
