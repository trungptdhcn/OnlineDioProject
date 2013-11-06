package com.example.OnlineDio.syncadapter.home;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class HomeFeedSynService extends Service
{
    private static final Object sSyncAdapterLock = new Object();
    private static HomeFeedSynAdapter sSyncAdapter = null;

    @Override
    public void onCreate()
    {
        synchronized (sSyncAdapterLock)
        {
            if (sSyncAdapter == null)
            {
                sSyncAdapter = new HomeFeedSynAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
