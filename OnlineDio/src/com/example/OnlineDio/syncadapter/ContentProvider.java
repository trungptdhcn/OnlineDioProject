package com.example.OnlineDio.syncadapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class ContentProvider extends android.content.ContentProvider
{
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String HOME_PATH = "homefeeds";
    public static final String HOME_PATH_FOR_ID = "homefeeds/*";

    public static final String PROFILE_PATH = "profilefeeds";
    public static final String PROFILE_PATH_FOR_ID = "profilefeeds/*";
    public static final int HOME_PATH_TOKEN = 1;
    public static final int HOME_PATH_FOR_ID_TOKEN = 2;

    public static final int PROFILE_PATH_TOKEN = 3;
    public static final int PROFILE_PATH_FOR_ID_TOKEN = 4;

    private static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProviderContract.AUTHORITY;
        matcher.addURI(authority, HOME_PATH, HOME_PATH_TOKEN);
        matcher.addURI(authority, HOME_PATH_FOR_ID, HOME_PATH_FOR_ID_TOKEN);
        matcher.addURI(authority, PROFILE_PATH, PROFILE_PATH_TOKEN);
        matcher.addURI(authority, PROFILE_PATH_FOR_ID, PROFILE_PATH_FOR_ID_TOKEN);
        return matcher;
    }

    // Content Provider stuff
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public String getType(Uri uri)
    {
        final int match = URI_MATCHER.match(uri);
        switch (match)
        {
            case HOME_PATH_TOKEN:
                return ProviderContract.CONTENT_TYPE_DIR;
            case HOME_PATH_FOR_ID_TOKEN:
                return ProviderContract.CONTENT_ITEM_TYPE;
            case PROFILE_PATH_TOKEN:
                return ProviderContract.PROFILE_TYPE_DIR;
            case PROFILE_PATH_FOR_ID_TOKEN:
                return ProviderContract.PROFILE_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        final int match = URI_MATCHER.match(uri);
        switch (match)
        {
            // retrieve tv shows list
            case HOME_PATH_TOKEN:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.HOMEFEED_TABLE_NAME);
                Cursor cur = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cur.setNotificationUri(getContext().getContentResolver(), uri);
                return cur;
            }
            case HOME_PATH_FOR_ID_TOKEN:
            {
                int homeFeedId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.HOMEFEED_TABLE_NAME);
                builder.appendWhere(DbHelper.HOMEFEED_COL_ID + "=" + homeFeedId);
                Cursor cur = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cur.setNotificationUri(getContext().getContentResolver(), uri);
                return cur;
            }
            case PROFILE_PATH_TOKEN:
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.PROFILE_TABLE_NAME);
                Cursor cur = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cur.setNotificationUri(getContext().getContentResolver(), uri);
                return cur;
            }
            case PROFILE_PATH_FOR_ID_TOKEN:
            {
                int homeFeedId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.PROFILE_TABLE_NAME);
                builder.appendWhere(DbHelper.PROFILE_ID + "=" + homeFeedId);
                Cursor cur = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                cur.setNotificationUri(getContext().getContentResolver(), uri);
                return cur;
            }

            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        int token = URI_MATCHER.match(uri);
        switch (token)
        {
            case HOME_PATH_TOKEN:
            {
                long id = db.insertOrThrow(DbHelper.HOMEFEED_TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return Uri.parse(ProviderContract.CONTENT_URI + "/" + id);
            }
            case PROFILE_PATH_TOKEN:
            {
                long id = db.insertOrThrow(DbHelper.PROFILE_TABLE_NAME, null, values);
                if (id != -1)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
//                return Uri.parse(ProviderContract.CONTENT_URI + "/" + id);
                return Uri.parse(ProviderContract.PROFILE_CONTENT_URI + "/" + id);
            }
            default:
            {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch (token)
        {
            case (HOME_PATH_TOKEN):
                rowsDeleted = db.delete(DbHelper.HOMEFEED_TABLE_NAME, selection, selectionArgs);
                break;
            case (HOME_PATH_FOR_ID_TOKEN):
            {
                String homeFeedIdWhereClause = DbHelper.HOMEFEED_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                {
                    homeFeedIdWhereClause += " AND " + selection;
                }
                rowsDeleted = db.delete(DbHelper.HOMEFEED_TABLE_NAME, homeFeedIdWhereClause, selectionArgs);
                break;
            }
            case (PROFILE_PATH_TOKEN):
            {
                rowsDeleted = db.delete(DbHelper.PROFILE_TABLE_NAME, selection, selectionArgs);
                break;
            }
            case (PROFILE_PATH_FOR_ID_TOKEN):
            {
                String homeFeedIdWhereClause = DbHelper.PROFILE_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                {
                    homeFeedIdWhereClause += " AND " + selection;
                }
                rowsDeleted = db.delete(DbHelper.PROFILE_TABLE_NAME, homeFeedIdWhereClause, selectionArgs);
                break;
            }

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        int token = URI_MATCHER.match(uri);
        int rowsUpdated = -1;
        switch (token)
        {
            case (HOME_PATH_TOKEN):
            {
                rowsUpdated = db.update(DbHelper.HOMEFEED_TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case (HOME_PATH_FOR_ID_TOKEN):
            {
                rowsUpdated = db.update(DbHelper.HOMEFEED_TABLE_NAME, values, DbHelper.HOMEFEED_COL_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? "AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            }
            case (PROFILE_PATH_TOKEN):
            {

            }

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (rowsUpdated != -1)
        {
            getContext().getContentResolver().notifyChange(uri, null, false);
        }
        return rowsUpdated;
    }
}
