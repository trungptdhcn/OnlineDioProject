package com.example.OnlineDio.utilities;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.example.OnlineDio.syncadapter.ProviderContract;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 05/11/2013
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class SyncUtils
{
    private static final long SYNC_FREQUENCY = 60 * 60;  // 1 hour (in seconds)
    private static final String CONTENT_AUTHORITY = ProviderContract.AUTHORITY;
    private static final String PREF_SETUP_COMPLETE = "setup_complete";

    public static void CreateSyncAccount(Context context, Account account)
    {
        boolean newAccount = false;
        boolean setupComplete = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        // Inform the system that this account supports sync
        ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
        // Inform the system that this account is eligible for auto sync when the network is up
        ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
        // Recommend a schedule for automatic synchronization. The system may modify this based
        // on other scheduled syncs and network utilization.
        ContentResolver.addPeriodicSync(
                account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);
        newAccount = true;

        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete)
        {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(account, ProviderContract.AUTHORITY, bundle);
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).commit();
        }
    }

}
