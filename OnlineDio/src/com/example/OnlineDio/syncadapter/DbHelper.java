package com.example.OnlineDio.syncadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "onlinedio.db";
    private static final int DATABASE_VERSION = 1;

    // DB Table Home
    public static final String HOMEFEED_TABLE_NAME = "homefeed";
    public static final String HOMEFEED_COL_ID = "_id";
    public static final String HOMEFEED_COL_ACCOUNT_ID = "account_id";
    public static final String HOMEFEED_COL_USER_ID = "user_id";
    public static final String HOMEFEED_COL_TITLE = "title";
    public static final String HOMEFEED_COL_THUMBNAIL = "thumbnail";
    public static final String HOMEFEED_COL_DESCRIPTION = "description";
    public static final String HOMEFEED_COL_SOUND_PATH = "sound_path";
    public static final String HOMEFEED_COL_DURATION = "duration";
    public static final String HOMEFEED_COL_PLAYED = "played";
    public static final String HOMEFEED_COL_CREATED_AT = "created_at";
    public static final String HOMEFEED_COL_UPDATED_AT = "updated_at";
    public static final String HOMEFEED_COL_LIKES = "likes";
    public static final String HOMEFEED_COL_VIEWED = "viewed";
    public static final String HOMEFEED_COL_COMMENTS = "comments";
    public static final String HOMEFEED_COL_USERNAME = "user_name";
    public static final String HOMEFEED_COL_DISPLAY_NAME = "display_name";
    public static final String HOMEFEED_COL_AVATAR = "avatar";

    public static final String HOME_TABLE_CREATE = "create table "
            + HOMEFEED_TABLE_NAME + "(" +
            HOMEFEED_COL_ID + " integer primary key autoincrement, " +
            HOMEFEED_COL_ACCOUNT_ID+ " integer, "+
            HOMEFEED_COL_USER_ID + " integer, " +
            HOMEFEED_COL_TITLE + " text, " +
            HOMEFEED_COL_THUMBNAIL + " text, " +
            HOMEFEED_COL_DESCRIPTION + " text, " +
            HOMEFEED_COL_SOUND_PATH + " text, " +
            HOMEFEED_COL_DURATION + " integer, " +
            HOMEFEED_COL_PLAYED + " integer, " +
            HOMEFEED_COL_CREATED_AT + " text, " +
            HOMEFEED_COL_UPDATED_AT + " text, " +
            HOMEFEED_COL_LIKES + " integer, " +
            HOMEFEED_COL_VIEWED + " integer, " +
            HOMEFEED_COL_COMMENTS + " integer, " +
            HOMEFEED_COL_USERNAME + " text, " +
            HOMEFEED_COL_DISPLAY_NAME + " text, " +
            HOMEFEED_COL_AVATAR + " text " +
            ");";
    //===================================================================================================
    //DB Table Profile
    public static final String PROFILE_TABLE_NAME = "profile";
    public static final String PROFILE_ID = "_id";
    public static final String PROFILE_FACEBOOK_ID = "facebook_id";
    public static final String PROFILE_USERNAME = "username";
    public static final String PROFILE_PASS = "password";
    public static final String PROFILE_AVATAR = "avatar";
    public static final String PROFILE_COVER_IMAGE = "cover_image";
    public static final String PROFILE_DISPLAY_NAME = "display_name";
    public static final String PROFILE_FULL_NAME = "full_name";
    public static final String PROFILE_PHONE = "phone";
    public static final String PROFILE_BIRTHDAY = "birthday";
    public static final String PROFILE_GENDER = "gender";
    public static final String PROFILE_COUNTRY_ID = "country_id";
    public static final String PROFILE_STORAGE_PLAN_ID = "storage_plan_id";
    public static final String PROFILE_DESCRIPTION = "description";
    public static final String PROFILE_CREATED_AT = "created_at";
    public static final String PROFILE_UPDATE_AT = "updated_at";
    public static final String PROFILE_SOUNDS = "sounds";
    public static final String PROFILE_FAVORITES = "favorites";
    public static final String PROFILE_LIKES = "likes";
    public static final String PROFILE_FOLLOWINGS = "followings";
    public static final String PROFILE_AUDIENCES = "audiences";

    public static final String PROFILE_TABLE_CREATE = "create table "
            + PROFILE_TABLE_NAME + "(" +
            PROFILE_ID + " integer primary key autoincrement, " +
            PROFILE_FACEBOOK_ID + " integer, " +
            PROFILE_USERNAME + " text, " +
            PROFILE_PASS + " text, " +
            PROFILE_AVATAR + " text, " +
            PROFILE_COVER_IMAGE + " text, " +
            PROFILE_DISPLAY_NAME + " text, " +
            PROFILE_FULL_NAME + " text, " +
            PROFILE_PHONE + " text, " +
            PROFILE_BIRTHDAY + " text, " +
            PROFILE_GENDER + " integer, " +
            PROFILE_COUNTRY_ID + " text, " +
            PROFILE_STORAGE_PLAN_ID + " integer, " +
            PROFILE_DESCRIPTION + " text, " +
            PROFILE_CREATED_AT + " text, " +
            PROFILE_UPDATE_AT + " text, " +
            PROFILE_SOUNDS + " integer, " +
            PROFILE_FAVORITES + " integer, " +
            PROFILE_LIKES + " integer, " +
            PROFILE_FOLLOWINGS + " integer, " +
            PROFILE_AUDIENCES + " integer " +
            ");";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(HOME_TABLE_CREATE);
        database.execSQL(PROFILE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + HOMEFEED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        onCreate(db);
    }
}
