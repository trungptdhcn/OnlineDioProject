package com.example.OnlineDio.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import com.example.OnlineDio.R;
import com.example.OnlineDio.activity.LoginActivity_;
import com.example.OnlineDio.activity.NavigationActivity;
import com.example.OnlineDio.activity.ProfileActivity;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.fragment.HomeFragment;
import com.example.OnlineDio.fragment.HomeFragment_;
import com.example.OnlineDio.syncadapter.ProviderContract;
import com.example.OnlineDio.util.ListNavigationAdapter;
import com.googlecode.androidannotations.annotations.*;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 12/11/2013
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class NavigationController
{
    ArrayList<String> data = new ArrayList<String>();

    @RootContext
    NavigationActivity navigationActivity;

    HomeFragment homeFragment = new HomeFragment_();

    @SystemService
    protected AccountManager accountManager;

    ActionBar actionBar;

    @Bean
    ListNavigationAdapter adapter;

//    @FragmentArg(AccountManager.KEY_AUTHTOKEN)
//    String authToken =  navigationActivity.getIntent().getStringExtra(AccountManager.KEY_AUTHTOKEN);
//    @FragmentArg(AccountManager.KEY_ACCOUNT_NAME)
//    String accountName =  navigationActivity.getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//    @FragmentArg("user_id")
//    String user_id = navigationActivity.getIntent().getStringExtra("user_id");

    protected PullToRefreshAttacher mPullToRefreshAttacher;

    public PullToRefreshAttacher getPullToRefreshAttacher()
    {
        return mPullToRefreshAttacher;
    }

    public void navigationListenerClicked(final int position)
    {
        navigationActivity.getDrawer().setDrawerListener(new DrawerLayout.SimpleDrawerListener()
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                switch (position)
                {
                    case 0:
                    {
                        final FragmentTransaction tx = navigationActivity.getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.navigation_main_FrameLayout, homeFragment, "homeFragment");
                        tx.commit();
                        break;
                    }
                    case 7:
                    {
                        Intent i = new Intent(navigationActivity, LoginActivity_.class);
                        Account accountResult = null;
                        Account[] accounts = accountManager.getAccountsByType(OnlineDioAccountGeneral.ACCOUNT_TYPE);
                        String name = navigationActivity.getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                        if (accounts.length != 0)
                        {
                            for (Account account : accounts)
                            {
                                if (account.name.equals(name))
                                {
                                    accountResult = account;
                                    break;
                                }
                            }
                            String user_id = navigationActivity.getIntent().getStringExtra("user_id");
                            Uri deleteDataUri = ProviderContract.CONTENT_URI.buildUpon().appendPath(user_id).build();
                            navigationActivity.getContentResolver().delete(deleteDataUri, null, null);
                            accountManager.removeAccount(accountResult, null, null);
                            navigationActivity.startActivity(i);
                            navigationActivity.finish();
                        }
                    }
                }

            }
        });
        navigationActivity.getDrawer().closeDrawer(navigationActivity.getLayoutDrawer());
    }


    public void createNavigationList()
    {
        data.add("Home");
        data.add("Favorite");
        data.add("Following");
        data.add("Audience");
        data.add("Genres");
        data.add("Setting");
        data.add("Help Center");
        data.add("Sign Out");
    }

    public void initObject()
    {
        actionBar = navigationActivity.getActionBar();
        mPullToRefreshAttacher = PullToRefreshAttacher.get(navigationActivity);
        actionBar.hide();
        createNavigationList();
        NavigationActivity.context = navigationActivity;
        adapter.setValues(data);
        navigationActivity.getNavList().setAdapter(adapter);
        parseAgumentToHomeFragment(homeFragment);
        FragmentTransaction tx = navigationActivity.getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.navigation_main_FrameLayout, homeFragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    protected void parseAgumentToHomeFragment(HomeFragment homeFragment)
    {
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_AUTHTOKEN, navigationActivity.getIntent().getStringExtra(AccountManager.KEY_AUTHTOKEN));
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, navigationActivity.getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
        bundle.putString("user_id", navigationActivity.getIntent().getStringExtra("user_id"));
        homeFragment.setArguments(bundle);
    }

    public void startProfileActivity()
    {
        Intent i = new Intent(navigationActivity, ProfileActivity.class);
        String user_id = navigationActivity.getIntent().getStringExtra("user_id");
        i.putExtra(AccountManager.KEY_AUTHTOKEN, navigationActivity.getIntent().getStringExtra(AccountManager.KEY_AUTHTOKEN));
        i.putExtra(AccountManager.KEY_ACCOUNT_NAME, navigationActivity.getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
        i.putExtra("user_id", user_id);
        Bundle b = navigationActivity.getIntent().getExtras();
        i.putExtras(b);
        navigationActivity.startActivity(i);
    }
}
