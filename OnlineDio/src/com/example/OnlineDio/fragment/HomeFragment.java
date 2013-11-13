package com.example.OnlineDio.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.OnlineDio.R;
import com.example.OnlineDio.activity.NavigationActivity;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;
import com.example.OnlineDio.util.HomeFeedAdapter;
import com.example.OnlineDio.utilities.Constant;
import com.example.OnlineDio.utilities.NetworkUtil;
import com.googlecode.androidannotations.annotations.*;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */

@EFragment(R.layout.home)
public class HomeFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener
{
    @ViewById(R.id.lvListSongs)
    protected static ListView home_lvFeeds;

    @ViewById(R.id.ibOption)
    protected ImageButton home_ibOption;

    protected LinearLayout layoutDrawer;
    DrawerLayout drawer;

    protected Account mConnectedAccount;

    @SystemService
    protected AccountManager accountManager;

    protected static HomeFeedAdapter mAdapter;

    protected PullToRefreshAttacher mPullToRefreshAttacher;

    @ViewById(R.id.ibDone)
    protected ImageButton home_ibNotify;

    @FragmentArg(AccountManager.KEY_AUTHTOKEN)
    String authToken;

    @FragmentArg(AccountManager.KEY_ACCOUNT_NAME)
    String accountName;

    @FragmentArg("user_id")
    String user_id;

    public static final int SIMULATED_REFRESH_LENGTH = 5000;
    protected static String[] FROM_COLUMS = new String[]{
            DbHelper.HOMEFEED_COL_TITLE,
            DbHelper.HOMEFEED_COL_DISPLAY_NAME,
            DbHelper.HOMEFEED_COL_COMMENTS,
            DbHelper.HOMEFEED_COL_LIKES,
            DbHelper.HOMEFEED_COL_UPDATED_AT,
            DbHelper.HOMEFEED_COL_AVATAR
    };

    protected static Cursor cur;

    protected static Dialog myPd_ring;
    public static Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.getData().getString("ok") != null)
            {
                setListHomeFeed(ProviderContract.CONTENT_URI);
                myPd_ring.dismiss();
            }
        }
    };

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        myPd_ring = new ProgressDialog(getActivity());
        synBeforeViewList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @UiThread
    protected void synBeforeViewList()
    {
        if (checkNetwork())
        {
            myPd_ring.show();
            syncAdapter();
        }
        else
        {
            Toast.makeText(getActivity(), "Check network connection", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, container, false);
        home_lvFeeds = (ListView) view.findViewById(R.id.lvListSongs);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
        layoutDrawer = (LinearLayout) getActivity().findViewById(R.id.left_drawer);
        mPullToRefreshAttacher = ((NavigationActivity) getActivity()).getNavigationController().getPullToRefreshAttacher();
//        Uri queryUri = ProviderContract.CONTENT_URI.buildUpon().appendPath(getArguments().getString("user_id")).build();
        Uri queryUri = ProviderContract.CONTENT_URI.buildUpon().appendPath(user_id).build();
        cur = NavigationActivity.context.getContentResolver().query(queryUri,
                null, null, null, null);
        if (cur.moveToFirst())
        {
            mAdapter = new HomeFeedAdapter(NavigationActivity.context, R.layout.home_row_of_listview2,
                    cur, FROM_COLUMS, new int[]{R.id.tvTitleOfSong, R.id.tvNameOfDirector,
                    R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                    R.id.tvNumberOfPostedDay, R.id.ivAvatars}, 0);
//            mAdapter.setCursor(cur);
            home_lvFeeds.setAdapter(mAdapter);
        }
        syncAdapter();
        return view;
    }

    @AfterViews
    void afterViews()
    {
        mPullToRefreshAttacher.addRefreshableView(home_lvFeeds, this);
    }

    @ItemClick(R.id.lvListSongs)
    void listHomeFeedsCliked()
    {
        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        tx.addToBackStack(null);
        tx.commit();
        tx.replace(R.id.navigation_main_FrameLayout, new ContentFragment_());
    }

    protected static void setListHomeFeed(Uri queryUri)
    {
        cur = NavigationActivity.context.getContentResolver().query(queryUri,
                null, null, null, null);
            mAdapter = new HomeFeedAdapter(NavigationActivity.context, R.layout.home_row_of_listview2,
                    cur, FROM_COLUMS, new int[]{R.id.tvTitleOfSong, R.id.tvNameOfDirector,
                    R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                    R.id.tvNumberOfPostedDay, R.id.ivAvatars}, 0);
//        mAdapter.setCursor(cur);
        home_lvFeeds.setAdapter(mAdapter);
    }

    @Click({R.id.ibOption})
    void homeButtonClicked(View clickedView)
    {
        switch (clickedView.getId())
        {
            case R.id.ibOption:
                drawer.openDrawer(layoutDrawer);
        }
    }

    @Override
    public void onRefreshStarted(View view)
    {
        backgroundRefresh();
    }

    @Background
    void backgroundRefresh()
    {
        try
        {
            Thread.sleep(SIMULATED_REFRESH_LENGTH);
            syncAdapter();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        uiUpdate();
    }

    @UiThread
    void uiUpdate()
    {
        mPullToRefreshAttacher.setRefreshComplete();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    //synAdapter ============================
    protected boolean checkNetwork()
    {
        Log.i("CheckNetwork", "Check network available");
        boolean result = true;
        String status = NetworkUtil.getConnectivityStatusString(NavigationActivity.context);
        if (status.equals(Constant.NOT_CONNECTED_TO_INTERNET.getValue()))
        {
            result = false;
        }
        return result;
    }

    protected void syncAdapter()
    {
        accountManager = AccountManager.get(getActivity());
        mConnectedAccount = new Account(accountName, "com.example.OnlineDio");
        String authority = ProviderContract.AUTHORITY;
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mConnectedAccount, authority, bundle);
    }
}