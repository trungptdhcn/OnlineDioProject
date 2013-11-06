package com.example.OnlineDio.syncadapter;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class ProviderContract
{
    //Home provider
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.onlinedio.homefeed";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.onlinedio.homefeed";
    public static final String AUTHORITY = "com.qsoft.onlinedio.feed.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private static final String PATH_ENTRIES = "homefeeds";
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

    //profile provider
    public static final String PROFILE_ITEM_TYPE = "vnd.android.cursor.item/vnd.onlinedio.profilefeed";
    public static final String PROFILE_TYPE_DIR = "vnd.android.cursor.dir/vnd.onlinedio.profilefeed";
    public static final Uri PROFILE_BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private static final String PROFILE_PATH_ENTRIES = "profilefeeds";

    public static final Uri PROFILE_CONTENT_URI =
            PROFILE_BASE_CONTENT_URI.buildUpon().appendPath(PROFILE_PATH_ENTRIES).build();

}
