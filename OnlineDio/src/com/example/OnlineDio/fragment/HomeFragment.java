package com.example.OnlineDio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    ListView lisView;
    private ImageButton home_ibOption;
    private LinearLayout layoutDrawer;
    private Account mConnectedAccount;
    private AccountManager accountManager;
    private SimpleCursorAdapter mAdapter;
    private ImageButton home_ibNotify;
    String[] PROJECTION = new String[]
            {
                    DbHelper.HOMEFEED_COL_ID,
                    DbHelper.HOMEFEED_COL_USER_ID,
                    DbHelper.HOMEFEED_COL_TITLE,
                    DbHelper.HOMEFEED_COL_THUMBNAIL,
                    DbHelper.HOMEFEED_COL_DESCRIPTION,
                    DbHelper.HOMEFEED_COL_SOUND_PATH,
                    DbHelper.HOMEFEED_COL_DURATION,
                    DbHelper.HOMEFEED_COL_PLAYED,
                    DbHelper.HOMEFEED_COL_CREATED_AT,
                    DbHelper.HOMEFEED_COL_UPDATED_AT,
                    DbHelper.HOMEFEED_COL_LIKES,
                    DbHelper.HOMEFEED_COL_VIEWED,
                    DbHelper.HOMEFEED_COL_COMMENTS,
                    DbHelper.HOMEFEED_COL_USERNAME,
                    DbHelper.HOMEFEED_COL_DISPLAY_NAME,
                    DbHelper.HOMEFEED_COL_AVATAR
            } ;
    String[] FROM_COLUMS = new String[]{
            DbHelper.HOMEFEED_COL_TITLE,
            DbHelper.HOMEFEED_COL_DISPLAY_NAME,
            DbHelper.HOMEFEED_COL_COMMENTS,
            DbHelper.HOMEFEED_COL_LIKES,
            DbHelper.HOMEFEED_COL_UPDATED_AT
    };
//    private static final int[] TO_FIELDS = new int[]{
//            R.id.text1,
//            android.R.id.text2
//    };

    private Object mSyncObserverHandle;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

    }

    private void syncAdapter()
    {
        String accountName = this.getArguments().getString(AccountManager.KEY_ACCOUNT_NAME);
        accountManager = AccountManager.get(getActivity());
        mConnectedAccount = new Account(accountName, "com.example.OnlineDio");
        String authority = ProviderContract.AUTHORITY;
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mConnectedAccount, authority, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, container, false);
        syncAdapter();
        lisView = (ListView) view.findViewById(R.id.lvListSongs);
        home_ibOption = (ImageButton) view.findViewById(R.id.ibOption);
        layoutDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);
        home_ibNotify = (ImageButton)view.findViewById(R.id.ibDone);
        home_ibNotify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                syncAdapter();
            }
        });
        new AsyncTask<String,String,String>()
        {

            @Override
            protected String doInBackground(String... params)
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        Cursor cur = getActivity().getContentResolver().query(ProviderContract.CONTENT_URI, null, null, null, null);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.home_row_of_listview2,
                cur, FROM_COLUMS, new int[]{R.id.tvTitleOfSong, R.id.tvNameOfDirector,
                R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                R.id.tvNumberOfPostedDay},0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder()
        {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i)
            {
                if (i == 16)
                {
                    // Convert timestamp to human-readable date
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
        lisView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        String authToken = accountManager.peekAuthToken(mConnectedAccount, OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id)
            {
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.navigation_main_FrameLayout, new ContentFragment());
                tx.addToBackStack(null);
                tx.commit();
            }
        });
        final DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
        home_ibOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawer.openDrawer(layoutDrawer);
            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSyncObserverHandle != null) {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSyncStatusObserver.onStatusChanged(0);

        // Watch for sync state changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
    }

    private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver()
    {
        /** Callback invoked with the sync adapter status changes. */
        @Override
        public void onStatusChanged(int which)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                /**
                 * The SyncAdapter runs on a background thread. To update the UI, onStatusChanged()
                 * runs on the UI thread.
                 */
                @Override
                public void run()
                {
                    // Create a handle to the account that was created by
                    // SyncService.CreateSyncAccount(). This will be used to query the system to
                    // see how the sync status has changed.
                    Account account = mConnectedAccount;
                    if (account == null)
                    {
                        // GetAccount() returned an invalid value. This shouldn't happen, but
                        // we'll set the status to "not refreshing".
                        return;
                    }

                    // Test the ContentResolver to see if the sync adapter is active or pending.
                    // Set the state of the refresh button accordingly.
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, ProviderContract.AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, ProviderContract.AUTHORITY);
                }
            });
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getActivity(), ProviderContract.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.changeCursor(null);
    }

}
