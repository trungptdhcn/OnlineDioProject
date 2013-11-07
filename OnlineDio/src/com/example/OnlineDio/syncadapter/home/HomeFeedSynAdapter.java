package com.example.OnlineDio.syncadapter.home;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.ParseComError;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class HomeFeedSynAdapter extends AbstractThreadedSyncAdapter
{
    private static final String TAG = "HomeFeedSyncAdapter";

    private final AccountManager mAccountManager;
    private final ContentResolver mContentResolver;

    public HomeFeedSynAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(final Account account, Bundle extras, String authority,
                              final ContentProviderClient provider, final SyncResult syncResult)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                try
                {
                    Log.i(TAG, "Perform sync data");

                    //get auth token
                    String authToken = mAccountManager.peekAuthToken(account,
                            OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);

                    Log.i(TAG, "Get data from server");
                    ParseHomeFeedInServer parseComService = new ParseHomeFeedInServer();
                    Object remoteDataObject = parseComService.getShows(authToken);
                    if (remoteDataObject instanceof ParseComError)
                    {
//                        authToken = OnlineDioAccountGeneral.sServerAuthenticate.userSignIn(account.name,
//                                mAccountManager.getPassword(account), account.type).getAccess_token();
                        mAccountManager.invalidateAuthToken(account.type, authToken);
                        String newToken = mAccountManager.getAuthToken(account, OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true, null, null)
                                .getResult().getString(AccountManager.KEY_AUTHTOKEN);
                        remoteDataObject = parseComService.getShows(newToken);
                    }
                    ArrayList<HomeFeedModel> remoteData = (ArrayList<HomeFeedModel>) remoteDataObject;
                    ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
                    HashMap<Long, HomeFeedModel> map = new HashMap<Long, HomeFeedModel>();
                    for (HomeFeedModel homeModel : remoteData)
                    {
                        map.put(homeModel.id, homeModel);
                    }

                    Log.i(TAG, "Get data on local");
                    Cursor cur = provider.query(ProviderContract.CONTENT_URI, null, null, null, null);
                    assert cur != null;
                    while (cur.moveToNext())
                    {
                        HomeFeedModel temp = HomeFeedModel.fromCursor(cur);
                        HomeFeedModel checkUpdate = map.get(temp.id);

                        if (checkUpdate != null)
                        {
                            //remove record map
                            map.remove(temp.id);
                            //Check update able
                            Uri existedUri = ProviderContract.CONTENT_URI.buildUpon()
                                    .appendPath(Long.toString(temp.id)).build();
                            if ((checkUpdate.updated_at != null) &&
                                    checkUpdate.updated_at.equals(temp.updated_at)
                                    || checkUpdate.likes != temp.likes
                                    || checkUpdate.comments != temp.comments)
                            {
                                Log.i(TAG, "Data start update");
                                batch.add(ContentProviderOperation.newUpdate(existedUri)
                                        .withValue(DbHelper.HOMEFEED_COL_ID, checkUpdate.id)
                                        .withValue(DbHelper.HOMEFEED_COL_USER_ID, checkUpdate.user_id)
                                        .withValue(DbHelper.HOMEFEED_COL_TITLE, checkUpdate.title)
                                        .withValue(DbHelper.HOMEFEED_COL_THUMBNAIL, checkUpdate.thumbnail)
                                        .withValue(DbHelper.HOMEFEED_COL_DESCRIPTION, checkUpdate.desccription)
                                        .withValue(DbHelper.HOMEFEED_COL_SOUND_PATH, checkUpdate.sound_path)
                                        .withValue(DbHelper.HOMEFEED_COL_DURATION, checkUpdate.duration)
                                        .withValue(DbHelper.HOMEFEED_COL_PLAYED, checkUpdate.played)
                                        .withValue(DbHelper.HOMEFEED_COL_CREATED_AT, checkUpdate.created_at)
                                        .withValue(DbHelper.HOMEFEED_COL_UPDATED_AT, checkUpdate.updated_at)
                                        .withValue(DbHelper.HOMEFEED_COL_LIKES, checkUpdate.likes)
                                        .withValue(DbHelper.HOMEFEED_COL_VIEWED, checkUpdate.viewed)
                                        .withValue(DbHelper.HOMEFEED_COL_COMMENTS, checkUpdate.comments)
                                        .withValue(DbHelper.HOMEFEED_COL_USERNAME, checkUpdate.username)
                                        .withValue(DbHelper.HOMEFEED_COL_DISPLAY_NAME, checkUpdate.display_name)
                                        .withValue(DbHelper.HOMEFEED_COL_AVATAR, checkUpdate.avatar).build());

                                syncResult.stats.numUpdates++;
                            }

                        }
                        // record not exist. Need remove it from db
                        else
                        {
                            Uri deleteUri = ProviderContract.CONTENT_URI.buildUpon().appendPath(Long.toString(temp.id)).build();
                            Log.i(TAG, "Start delete" + deleteUri);
                            batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                            syncResult.stats.numDeletes++;
                        }
                    }
                    cur.close();
                    // add new record
                    for (HomeFeedModel homeModel : map.values())
                    {
                        Log.i(TAG, "Start insert: record_id = " + homeModel.id);
                        batch.add(ContentProviderOperation
                                .newInsert(ProviderContract.CONTENT_URI)
                                .withValue(DbHelper.HOMEFEED_COL_ID, homeModel.id)
                                .withValue(DbHelper.HOMEFEED_COL_USER_ID, homeModel.user_id)
                                .withValue(DbHelper.HOMEFEED_COL_TITLE, homeModel.title)
                                .withValue(DbHelper.HOMEFEED_COL_THUMBNAIL, homeModel.thumbnail)
                                .withValue(DbHelper.HOMEFEED_COL_DESCRIPTION, homeModel.desccription)
                                .withValue(DbHelper.HOMEFEED_COL_SOUND_PATH, homeModel.sound_path)
                                .withValue(DbHelper.HOMEFEED_COL_DURATION, homeModel.duration)
                                .withValue(DbHelper.HOMEFEED_COL_PLAYED, homeModel.played)
                                .withValue(DbHelper.HOMEFEED_COL_CREATED_AT, homeModel.created_at)
                                .withValue(DbHelper.HOMEFEED_COL_UPDATED_AT, homeModel.updated_at)
                                .withValue(DbHelper.HOMEFEED_COL_LIKES, homeModel.likes)
                                .withValue(DbHelper.HOMEFEED_COL_VIEWED, homeModel.viewed)
                                .withValue(DbHelper.HOMEFEED_COL_COMMENTS, homeModel.comments)
                                .withValue(DbHelper.HOMEFEED_COL_USERNAME, homeModel.username)
                                .withValue(DbHelper.HOMEFEED_COL_DISPLAY_NAME, homeModel.display_name)
                                .withValue(DbHelper.HOMEFEED_COL_AVATAR, homeModel.avatar).build());
                        syncResult.stats.numInserts++;
                    }
                    mContentResolver.applyBatch(ProviderContract.AUTHORITY, batch);
                    mContentResolver.notifyChange(ProviderContract.CONTENT_URI, // URI
                            null, // No local observer
                            false); // IMPORTANT: Do not sync to network

                    Log.d(TAG, "Finished sync data");

                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                catch (OperationApplicationException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return null;
            }
        }.execute();

    }
}
